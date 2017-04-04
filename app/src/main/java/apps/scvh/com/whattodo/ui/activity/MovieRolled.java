package apps.scvh.com.whattodo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.omertron.omdbapi.OmdbApi;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.MovieWatchPicker;
import apps.scvh.com.whattodo.util.UIHandler;
import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import io.reactivex.Observable;

public class MovieRolled extends FragmentActivity {

    private UIHandler handler;

    //Heh
    private TextView title;
    private TextView fullText;
    private TextView year;
    private TextView meter;
    private TextView director;
    private TextView actors;
    private TextView awards;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rolled);
        ImdbRandomMoviePicker randomMoviePicker = new ImdbRandomMoviePicker();
        ImdbWorker worker = new ImdbWorker(this, new OmdbApi());
        ImdbRandomMovieListGenerator generator = new ImdbRandomMovieListGenerator(this, worker);
        handler = new UIHandler(new MovieWatchPicker(randomMoviePicker, worker, generator));
        initFields();
        setMovie(handler.getMovieObservable());

    }

    private void setPicture(Observable<Drawable> coverObservable) {
        coverObservable.subscribe(cover -> picture.setBackground(cover));
    }

    private void setMovie(Observable<Movie> movieObservable) {
        movieObservable.subscribe(movie -> {
            title.setText(movie.getName());
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
            if (movie.getPictureId().equals(getString(R.string.imdb_na))) {
                picture.setBackground(getDrawable(R.mipmap.placeholder));
            } else {
                setPicture(handler.getPicture(movie.getPictureId()));
            }
        });
    }

    private void initFields() {
        title = (TextView) findViewById(R.id.title);
        fullText = (TextView) findViewById(R.id.fullText);
        year = (TextView) findViewById(R.id.year);
        meter = (TextView) findViewById(R.id.meter);
        director = (TextView) findViewById(R.id.director);
        actors = (TextView) findViewById(R.id.actors);
        awards = (TextView) findViewById(R.id.awards);
        picture = (ImageView) findViewById(R.id.cover);
    }
}
