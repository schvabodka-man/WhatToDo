package apps.scvh.com.whattodo.util.di;

import android.content.Context;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import apps.scvh.com.whattodo.util.omdbImplementation.OmdbAPIRetriever;
import apps.scvh.com.whattodo.util.workers.Filterer;
import apps.scvh.com.whattodo.util.workers.GotoImdb;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;
import apps.scvh.com.whattodo.util.workers.MovieWatchPicker;
import apps.scvh.com.whattodo.util.workers.UIHandler;
import dagger.Module;
import dagger.Provides;

/**
 * ENG class that i need for DI'ing all the stuff
 * RUS класс который я юзаю для внедрения зависимостей
 */
@Singleton
@Module
public class DaggerProvider {

    private Context context;

    public DaggerProvider(Context context) {
        this.context = context;
    }

    @Provides
    @Named("ImdbRandomMoviePicker")
    ImdbRandomMoviePicker provideRandomMoviePicker() {
        return new ImdbRandomMoviePicker();
    }

    @Provides
    @Named("ImdbWorker")
    ImdbWorker provideWorker() {
        return new ImdbWorker(context, new OmdbAPIRetriever());
    }

    @Provides
    @Named("ListGenerator")
    ImdbRandomMovieListGenerator provieListGenerator(@Named
                                                             ("ImdbWorker") ImdbWorker worker,
                                                     @Named("Ignore") IgnoringHelper helper,
                                                     @Named("Filterer") Filterer filterer) {
        return new ImdbRandomMovieListGenerator(context, worker, helper, filterer);
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

    @Provides
    @Named("ImdbLinker")
    GotoImdb provideGotoImdbHandler() {
        return new GotoImdb(context);
    }

    @Provides
    @Named("Filterer")
    Filterer provideFilterer() {
        return new Filterer(PreferenceManager.getDefaultSharedPreferences(context), context);
    }
}
