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


/**
 * ENG class that handles to provide data for UI
 * RUS класс доставляющий все данные для ГУЯ
 */
public class UIHandler {

    private MovieWatchPicker picker;

    public UIHandler(MovieWatchPicker picker) {
        this.picker = picker;
    }

    /**
     * RUS просто типапрокся чтобы постучать к методу в пикере
     * ENG simply calling method in picker
     * @return the movie observable
     */
    public Observable<Movie> getMovieObservable() {
        return picker.getMovieObservable();
    }

    /**
     * ENG method that's getting picture from url
     * RUS получает обложку
     * @param id url of movie
     * @return the observable with picture
     */
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
