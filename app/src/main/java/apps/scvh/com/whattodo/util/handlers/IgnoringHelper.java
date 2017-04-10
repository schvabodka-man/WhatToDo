package apps.scvh.com.whattodo.util.handlers;

import java.util.List;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IgnoringHelper {

    public void ignoreMovie(Movie movie) {
        Observable.just(movie).subscribeOn(Schedulers.io()).subscribe(getMovie -> movie.save());
        //i like that
    }

    public void unignoreMovie(Movie movie) {
        Observable.just(movie).subscribeOn(Schedulers.io()).subscribe(getMovie -> movie.delete())
        ; //i like that
    }

    public boolean isIgnored(Movie movie) {
        List<Movie> result = Movie.find(Movie.class, "imdb_id = ?", String.valueOf(movie
                .getImdbId()));
        return !result.isEmpty();
    }

    public Observable<List<Movie>> getListOfIgnoredMovies() {
        return Observable.defer(new Callable<ObservableSource<List<Movie>>>() {
            @Override
            public ObservableSource<List<Movie>> call() throws Exception {
                return Observable.just(Movie.listAll(Movie.class));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
