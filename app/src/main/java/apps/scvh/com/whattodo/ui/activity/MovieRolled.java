package apps.scvh.com.whattodo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.orm.SugarContext;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.workers.GotoImdb;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;
import apps.scvh.com.whattodo.util.workers.UIHandler;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Movie rolled.
 */
public class MovieRolled extends FragmentActivity {

    @Inject
    @Named("UIHandler")
    UIHandler handler;
    @Inject
    @Named("Ignore")
    IgnoringHelper ignoringHelper;
    @Inject
    @Named("ImdbLinker")
    GotoImdb gotoLinkHandler;

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
    @BindView(R.id.redraw)
    Button redraw;
    @BindView(R.id.ignore)
    Button ignore;
    @BindView(R.id.imdb_button)
    Button imdbButton;

    private ProgressDialog dialog;

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
            case R.id.settings_menu:
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setPicture(Observable<Drawable> coverObservable) {
        coverObservable.subscribe(cover ->
                picture.setBackground(cover));
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
                picture.setVisibility(View.GONE);
            } else {
                picture.setVisibility(View.VISIBLE);
                setPicture(handler.getPicture(movie.getPictureId()));
            }
            redraw.setVisibility(View.VISIBLE);
            imdbButton.setVisibility(View.VISIBLE);
            ignore.setVisibility(View.VISIBLE);
            dialog.dismiss();
        });
    }

    private void visualLoading() {
        redraw.setVisibility(View.INVISIBLE); //this and next invisible buttons is just for
        // beautifying loading
        imdbButton.setVisibility(View.INVISIBLE);
        ignore.setVisibility(View.INVISIBLE);
        dialog = new ProgressDialog(this);
        dialog.setOnCancelListener(dialog1 -> {
            if (this.getActionBar().getTitle().equals(this.getString(R.string.app_name))) {
                finish();
            } else {
                try {
                    Schedulers.computation().shutdown();
                    Schedulers.io().shutdown();
                } catch (Exception exception) {
                    Log.i("i m ignoring", "this");
                }
            }
        });
        dialog.setTitle(getString(R.string.loading));
        dialog.setMessage(getString(R.string.please_wait));
        dialog.show();
    }

    private void init() {
        ButterKnife.bind(this);
        DaggerInjector injector = new DaggerInjector(this);
        injector.getComponent().inject(this);
        SugarContext.init(this);
    }

    private void setOnClickListeners() {
        RxView.clicks(redraw).subscribe(t -> {
            visualLoading();
            setMovie(handler.getMovieObservable());
            redraw.setVisibility(View.VISIBLE);
            imdbButton.setVisibility(View.VISIBLE);
            ignore.setVisibility(View.VISIBLE);
        });
        RxView.clicks(ignore).subscribe(t -> {
            ignoringHelper.ignoreMovie(movieFromObservable);
            Toast.makeText(this, R.string.movie_ignored, Toast.LENGTH_SHORT).show();
        });
        RxView.clicks(imdbButton).subscribe(t -> gotoLinkHandler
                .gotoMoviePage(movieFromObservable));
    }
}
