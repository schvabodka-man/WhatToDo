package apps.scvh.com.whattodo.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.orm.SugarContext;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.handlers.GotoImdbHandler;
import apps.scvh.com.whattodo.util.handlers.IgnoringHelper;
import apps.scvh.com.whattodo.util.handlers.UIHandler;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class MovieRolled extends FragmentActivity {

    @Inject
    @Named("UIHandler")
    UIHandler handler;
    @Inject
    @Named("Ignore")
    IgnoringHelper ignoringHelper;
    @Inject
    @Named("ImdbLinker")
    GotoImdbHandler gotoLinkHandler;

    //Heh, they're all actually public because butter knife need them to be public, not private
    @BindView(R.id.fullText)
    TextView fullText;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.meter)
    TextView meter;
    @BindView(R.id.director)
    TextView director;
    @BindView(R.id.actors)
    TextView actors;
    @BindView(R.id.awards)
    TextView awards;
    @BindView(R.id.genre)
    TextView genre;
    @BindView(R.id.cover)
    ImageView picture;

    private Movie movieFromObservable; //need for implementing ignoring of movies

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rolled);
        init();
        visualLoading();
        setOnClickListeners();
        setMovie(handler.getMovieObservable());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rolled_movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ignored_movies_list_menu:
                Intent intent = new Intent(this, IgnoredMovies.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setPicture(Observable<Drawable> coverObservable) {
        coverObservable.subscribe(cover -> picture.setBackground(cover));
    }

    private void setMovie(Observable<Movie> movieObservable) {
        movieObservable.subscribe(movie -> {
            movieFromObservable = movie;
            getActionBar().setTitle(movie.getName());
            fullText.setText(movie.getDescription());
            year.setText(movie.getYear());
            if (movie.getMetacriticScore() == 0) {
                meter.setText(getString(R.string.metascore_na));
            } else {
                meter.setText(getString(R.string.metascore) + movie.getMetacriticScore());
            }
            director.setText(getString(R.string.director) + movie.getDirector());
            actors.setText(getString(R.string.actors) + movie.getActors());
            awards.setText(getString(R.string.awards) + movie.getAwards());
            genre.setText(movie.getGenre());
            if (movie.getPictureId().equals(getString(R.string.imdb_na))) {
                picture.setBackground(getDrawable(R.mipmap.placeholder));
            } else {
                setPicture(handler.getPicture(movie.getPictureId()));
            }
            findViewById(R.id.progress).setVisibility(View.GONE);
        });
    }

    private void visualLoading() {
        getActionBar().setTitle(getString(R.string.getting_movie));
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
    }

    private void init() {
        ButterKnife.bind(this);
        DaggerInjector injector = new DaggerInjector(this);
        injector.getComponent().inject(this);
        SugarContext.init(this);
    }

    private void setOnClickListeners() {
        RxView.clicks(findViewById(R.id.redraw)).subscribe(t -> {
            visualLoading();
            setMovie(handler.getMovieObservable());
        });
        RxView.clicks(findViewById(R.id.ignore)).subscribe(t ->
                ignoringHelper.ignoreMovie(movieFromObservable));
        RxView.clicks(findViewById(R.id.imdb_button)).subscribe(t -> gotoLinkHandler
                .gotoMoviePage(movieFromObservable));
    }
}
