package apps.scvh.com.whattodo.util.workers;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.essences.Movie;

public class Filterer {

    private SharedPreferences preferences;
    private Context context;

    public Filterer(SharedPreferences preferences, Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    public boolean isFiltered(Movie movie) {
        if (isFilteredByPictures(movie) == true) {
            Log.i(context.getString(R.string.ignore), context.getString(R.string.by_pic));
            return true;
        } else if (true == isFilteredByAwards(movie)) {
            Log.i(context.getString(R.string.ignore), context.getString(R.string.by_award));
            return true;
        } else if ("true".equals(String.valueOf(isFilteredByScore(movie)))) {
            Log.i(context.getString(R.string.ignore), context.getString(R.string.by_score));
            return true;
        } else {
            return false;
        }
    }

    private boolean isFilteredByPictures(Movie movie) {
        if (Boolean.toString(isFilteringByPicEnabled()).equals("true")) {
            if (movie.getPictureId().equals(context.getString(R.string.imdb_na))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isFilteredByAwards(Movie movie) {
        if (!"false".equals(String.valueOf(isFilteringByAwardsEnabled()))) {
            if (movie.getAwards().equals(context.getString(R.string.imdb_na))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isFilteredByScore(Movie movie) {
        if (true == isFilteringByScoreEnabled()) {
            if (movie.getMetacriticScore() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isFilteringByPicEnabled() {
        return preferences.getBoolean(context.getString(R.string.only_with_pic_prefs_key), false);
    }

    private boolean isFilteringByAwardsEnabled() {
        return preferences.getBoolean(context.getString(R.string.only_with_awards_prefs_key),
                false);
    }

    private boolean isFilteringByScoreEnabled() {
        return preferences.getBoolean(context.getString(R.string.only_with_score_prefs_key), false);
    }
}
