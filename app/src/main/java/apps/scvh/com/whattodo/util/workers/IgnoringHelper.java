package apps.scvh.com.whattodo.util.workers;

import java.util.List;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.MovieConverted;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ENG class used for SQL operations with ignored movies
 * RUS класс который я юзаю чтобы игнорить фильмы
 */
public class IgnoringHelper {

    public void ignoreMovie(MovieConverted movieConverted) {
        Observable.just(movieConverted).subscribeOn(Schedulers.io()).subscribe(getMovie -> movieConverted.save());
        //i like that
    }

    public void unignoreMovie(MovieConverted movieConverted) {
        Observable.just(movieConverted).subscribeOn(Schedulers.io()).subscribe(getMovie -> movieConverted.delete())
        ; //i like that
    }

    public boolean isIgnored(MovieConverted movieConverted) {
        List<MovieConverted> result = MovieConverted.find(MovieConverted.class, "imdb_id = ?", String.valueOf(movieConverted
                .getImdbId()));
        return !result.isEmpty();
    }

    public Observable<List<MovieConverted>> getListOfIgnoredMovies() {
        return Observable.defer(new Callable<ObservableSource<List<MovieConverted>>>() {
            @Override
            public ObservableSource<List<MovieConverted>> call() throws Exception {
                return Observable.just(MovieConverted.listAll(MovieConverted.class));
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
