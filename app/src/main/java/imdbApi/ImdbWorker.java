package imdbApi;


import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import apps.scvh.com.whattodo.R;
import essences.Movie;

public class ImdbWorker {

    private Context context;

    public ImdbWorker(Context context) {
        this.context = context;
    }

    public Movie getMovie(int Id) {
        return null;
    }

    public int getMovieStats() {
        Log.d("stats", "Getting stats");
        Document imdbStats;
        Elements numberOfMovies = new Elements();
        try {
            imdbStats = Jsoup.connect(String.valueOf(context.getResources().getText(R.string.imdb_stats))).get();
            numberOfMovies = imdbStats.select(String.valueOf(context.getResources().getText(R.string.imdb_css_selector))); //that's a magic, they don't have normal api for this
        } catch (IOException e) {
            e.printStackTrace();
        }
        int movieNumber = Integer.parseInt(numberOfMovies.get(28).text().replace(",", ""));
        Log.d("stats", "There are " + movieNumber + " movies");
        return movieNumber;
    }
}
