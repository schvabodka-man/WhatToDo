package apps.scvh.com.whattodo.util.workers;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.essences.Movie;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;

/**
 * ENG class i'm using for simly going to imdb page of movie
 * RUS класс который я юзаю чтобы открывать ссылки на кино в браузере
 */
public class GotoImdb {

    private Context context;

    public GotoImdb(Context context) {
        this.context = context;
    }

    public void gotoMoviePage(Movie movie) {
        getMovieLink(movie).subscribe(url -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        });
    }

    private Observable<String> getMovieLink(final Movie movie) {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                String movieUrl = context.getString(R.string.imdb_link) + String.format(Locale
                        .US, context.getString(R
                        .string.imdb_leading_zeroes), movie.getImdbId()); //This is for zeroes at
                // the beginning of id
                return Observable.just(movieUrl);
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
