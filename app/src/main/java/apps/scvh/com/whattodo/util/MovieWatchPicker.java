package apps.scvh.com.whattodo.util;

import java.util.HashSet;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * ENG Simple decorator to hack all together
 * RUS просто декоратор чтобы просто получить все нужные приколы
 */
public class MovieWatchPicker {

    private ImdbRandomMoviePicker randomMoviePicker;
    private ImdbWorker worker;
    private ImdbRandomMovieListGenerator listGenerator;

    private final int NUMBER_OF_MOVIES = 8;

    public MovieWatchPicker(ImdbRandomMoviePicker randomMoviePicker, ImdbWorker worker,
                            ImdbRandomMovieListGenerator listGenerator) {
        this.randomMoviePicker = randomMoviePicker;
        this.worker = worker;
        this.listGenerator = listGenerator;
    }

    /**
     * ENG Just getting observable with best movie
     * RUS Метод просто достающим обсервер с лучшим кинцом
     * @return the movie observable
     */
    Observable<Movie> getMovieObservable() {
        return Observable.defer(new Callable<Observable<Movie>>() {
            @Override
            public Observable<Movie> call() throws Exception {
                int maxId = worker.getMovieStats();
                HashSet<Integer> random = listGenerator.getListOfRandomId(NUMBER_OF_MOVIES, maxId);
                HashSet<Movie> movies = listGenerator.getListOfMovies(random, maxId);
                Movie bestOne = randomMoviePicker.pickBestMovie(movies);
                return Observable.just(bestOne);
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }


}
