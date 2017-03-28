package apps.scvh.com.whattodo.util;


import android.content.Context;

import apps.scvh.com.whattodo.util.essences.Movie;

public class UIHandler {

    private Context context;
    private MovieWatchPicker picker;

    public UIHandler(Context context, MovieWatchPicker picker) {
        this.context = context;
        this.picker = picker;
    }

    public void setBestMovie() {
        Movie bestOne = picker.movieReceive();
    }
}
