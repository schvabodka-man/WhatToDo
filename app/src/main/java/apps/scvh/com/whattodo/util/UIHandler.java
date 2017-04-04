package apps.scvh.com.whattodo.util;


import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class UIHandler {

    private MovieWatchPicker picker;

    public UIHandler(MovieWatchPicker picker) {
        this.picker = picker;
    }

    public Observable<Movie> getMovieObservable() {
        return picker.getMovieObservable();
    }

    public Observable<Drawable> getPicture(final String id) {
        return Observable.defer(new Callable<ObservableSource<Drawable>>() {
            @Override
            public ObservableSource<Drawable> call() throws Exception {
                InputStream address = null;
                try {
                    address = (InputStream) new URL(id).getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable cover = Drawable.createFromStream(address, "coverSrc");
                return Observable.just(cover);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
