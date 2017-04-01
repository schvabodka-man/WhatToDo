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

    public UIHandler(Context context, MovieWatchPicker picker) {
        this.context = context;
        this.picker = picker;
    }

    private void setBestMovie(Observable<Movie> observable) {
        TextView forWorking; //There goes a dirty hack, hard to explain why i done it in that way
        Movie movie = observable.blockingFirst();
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.fullText);
        forWorking.setText(movie.getDescription());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.year);
        forWorking.setText(movie.getYear());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.meter);
        if (movie.getMetacriticScore() == 0) {
            forWorking.setText(context.getString(R.string.metascore_na));
        } else {
            forWorking.setText(context.getString(R.string.metascore) + movie.getMetacriticScore()
            ); // yes i know
        }
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.title);
        forWorking.setText(movie.getName());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.actors);
        forWorking.setText(movie.getActors());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.awards);
        forWorking.setText(movie.getAwards());
        forWorking = (TextView)
                ((Activity) context).findViewById(R.id.director);
        forWorking.setText(movie.getDirector());
    }

    public void setMovieFront() {
        setBestMovie(picker.getMovieObservable());
    }
}
