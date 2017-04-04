package apps.scvh.com.whattodo.util.di;

import android.content.Context;

import com.omertron.omdbapi.OmdbApi;

import javax.inject.Named;

import apps.scvh.com.whattodo.util.MovieWatchPicker;
import apps.scvh.com.whattodo.util.UIHandler;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import dagger.Module;
import dagger.Provides;

@Module
public class DaggerImdbProvider {

    @Provides
    @Named("ImdbRandomMoviePicker")
    ImdbRandomMoviePicker provideRandomMoviePicker() {
        return new ImdbRandomMoviePicker();
    }

    @Provides
    @Named("ImdbWorker")
    ImdbWorker provideWorker(@Named("Context") Context context) {
        return new ImdbWorker(context, new OmdbApi());
    }

    @Provides
    @Named("ListGenerator")
    ImdbRandomMovieListGenerator provieListGenerator(@Named("Context") Context context, @Named
            ("ImdbWorker") ImdbWorker worker) {
        return new ImdbRandomMovieListGenerator(context, worker);
    }

    @Provides
    @Named("UIHandler")
    UIHandler provideUIHandler(@Named("MoviePicker") MovieWatchPicker movieWatchPicker) {
        return new UIHandler(movieWatchPicker);
    }

    @Provides
    @Named("MoviePicker")
    MovieWatchPicker providePicker(@Named("ImdbWorker") ImdbWorker worker, @Named
            ("ImdbRandomMoviePicker") ImdbRandomMoviePicker randomMoviePicker, @Named
                                           ("ListGenerator") ImdbRandomMovieListGenerator
                                           listGenerator) {
        return new MovieWatchPicker(randomMoviePicker, worker, listGenerator);
    }
}
