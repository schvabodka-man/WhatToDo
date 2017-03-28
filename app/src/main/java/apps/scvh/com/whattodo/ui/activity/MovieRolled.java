package apps.scvh.com.whattodo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.omertron.omdbapi.OmdbApi;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.MovieWatchPicker;
import apps.scvh.com.whattodo.util.UIHandler;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;

public class MovieRolled extends FragmentActivity {

    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rolled);
        ImdbRandomMoviePicker randomMoviePicker = new ImdbRandomMoviePicker();
        ImdbWorker worker = new ImdbWorker(this, new OmdbApi());
        ImdbRandomMovieListGenerator generator = new ImdbRandomMovieListGenerator(this, worker);
        handler = new UIHandler(this, new MovieWatchPicker(randomMoviePicker, worker, generator));
        setBestMovie();
    }

    private void setBestMovie() {
        handler.setBestMovie();
    }
}
