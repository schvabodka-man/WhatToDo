package apps.scvh.com.whattodo.util;


import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.essences.Movie;
import io.reactivex.Observable;

public class UIHandler {

    private Context context;
    private MovieWatchPicker picker;

    //There goes a dirty hack, hard to explain why i done it in that way bug at all it works and
    // it clean

    public UIHandler(Context context, MovieWatchPicker picker) {
        this.context = context;
        this.picker = picker;
    }

    private void setBestMovie(Observable<Movie> observable) {
        TextView forWorking;
        Movie movie = observable.blockingFirst();
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.fullText);
        forWorking.setText(movie.getDescription());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.year);
        forWorking.setText(movie.getYear());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.meter);
        forWorking.setText(movie.getMetacriticScore());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.title);
        forWorking.setText(movie.getName());
    }

    public void setMovieFront() {
        setBestMovie(picker.getMovieObservable());
    }
}
