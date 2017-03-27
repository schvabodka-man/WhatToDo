package apps.scvh.com.whattodo.util;

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

    //why the hell i can't assign something from inner class into local variable
    private Movie bestMovie = new Movie();

    private final int NUMBER_OF_MOVIES = 8;

    public MovieWatchPicker(ImdbRandomMoviePicker randomMoviePicker, ImdbWorker worker,
                            ImdbRandomMovieListGenerator listGenerator) {
        this.randomMoviePicker = randomMoviePicker;
        this.worker = worker;
        this.listGenerator = listGenerator;
    }

    public Observable<Movie> getMovieObservable() {
        Observable<Movie> moviePick = Observable.defer(new Callable<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() throws Exception {
                return Observable.just(worker.getMovieStats());
            }
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(maxId -> listGenerator.getListOfRandomId(NUMBER_OF_MOVIES, maxId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(indexSet -> listGenerator.getListOfMovies(indexSet, worker.getMovieStats()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(movieSet -> randomMoviePicker.pickBestMovie(movieSet));
        return moviePick;
    }

    public Movie getBestMovie(Observable<Movie> observable) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> movie = bestMovie);
        return bestMovie;
    }

}
