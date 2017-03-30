package apps.scvh.com.whattodo.util;

import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import io.reactivex.Observable;
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

    Observable<Movie> getMovieObservable() {
        return Observable.defer(new Callable<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() throws Exception {
                return Observable.just(worker.getMovieStats());
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(maxId -> listGenerator.getListOfRandomId(NUMBER_OF_MOVIES, maxId))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(indexSet -> listGenerator.getListOfMovies(indexSet, worker.getMovieStats()))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .map(movieSet -> randomMoviePicker.pickBestMovie(movieSet));
    }


}
