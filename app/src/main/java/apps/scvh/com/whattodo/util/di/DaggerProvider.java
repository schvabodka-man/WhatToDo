package apps.scvh.com.whattodo.util.di;

import android.content.Context;

import com.omertron.omdbapi.OmdbApi;

import javax.inject.Named;
import javax.inject.Singleton;

import apps.scvh.com.whattodo.util.handlers.IgnoringHelper;
import apps.scvh.com.whattodo.util.handlers.MovieWatchPicker;
import apps.scvh.com.whattodo.util.handlers.UIHandler;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import dagger.Module;
import dagger.Provides;

/**
 * ENG class that i need for DI'ing all the stuff
 * RUS класс который я юзаю для внедрения зависимостей
 */
@Singleton
@Module
public class DaggerProvider {

    public DaggerProvider(Context context) {
        this.context = context;
    }

    private Context context;

    @Provides
    @Named("ImdbRandomMoviePicker")
    ImdbRandomMoviePicker provideRandomMoviePicker() {
        return new ImdbRandomMoviePicker();
    }

    @Provides
    @Named("ImdbWorker")
    ImdbWorker provideWorker() {
        return new ImdbWorker(context, new OmdbApi());
    }

    @Provides
    @Named("ListGenerator")
    ImdbRandomMovieListGenerator provieListGenerator(@Named
                                                             ("ImdbWorker") ImdbWorker worker,
                                                     @Named("Ignore") IgnoringHelper helper) {
        return new ImdbRandomMovieListGenerator(context, worker, helper);
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

    @Provides
    @Named("Ignore")
    IgnoringHelper provideIgnoringHelper() {
        return new IgnoringHelper();
    }
}
