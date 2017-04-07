package apps.scvh.com.whattodo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.orm.SugarContext;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.IgnoringHelper;
import apps.scvh.com.whattodo.util.UIHandler;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.Movie;
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
        ButterKnife.bind(this);
        DaggerInjector injector = new DaggerInjector(this);
        injector.getComponent().inject(this);
        SugarContext.init(this);
        setMovie(handler.getMovieObservable());
        RxView.clicks(findViewById(R.id.redraw)).subscribe(t -> {
            this.recreate();
        });
        RxView.clicks(findViewById(R.id.ignore)).subscribe(t -> {
            ignoringHelper.ignoreMovie(movieFromObservable);
        });
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
                meter.setText(getString(R.string.metascore) + movie.getMetacriticScore()); // yes
                // i know
            }
            director.setText(movie.getDirector());
            actors.setText(movie.getActors());
            awards.setText(movie.getAwards());
            genre.setText(movie.getGenre());
            if (movie.getPictureId().equals(getString(R.string.imdb_na))) {
                picture.setBackground(getDrawable(R.mipmap.placeholder));
            } else {
                setPicture(handler.getPicture(movie.getPictureId()));
            }
            findViewById(R.id.progress).setVisibility(View.GONE);
        });
    }
}
