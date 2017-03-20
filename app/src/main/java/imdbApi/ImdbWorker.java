package imdbApi;


import android.content.Context;
import android.util.Log;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.tools.OmdbBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

import apps.scvh.com.whattodo.R;
import essences.Movie;
import essences.MovieBuilder;

public class ImdbWorker {

    private Context context;
    private OmdbApi omdbApi;

    public ImdbWorker(Context context, OmdbApi omdbApi) {
        this.context = context;
        this.omdbApi = omdbApi;
    }

    public Movie getMovie(int id) {
        MovieBuilder movieBuilder = new MovieBuilder();
        Movie movie = new Movie();
        String withLeadingZeroes = String.format(Locale.US, "%07d", id);
        try {
            OmdbVideoFull result = omdbApi.getInfo(new OmdbBuilder().setImdbId(String.valueOf
                    (context.getResources().getText(R.string.imdb_prefix)) + withLeadingZeroes)
                    .build());
            movieBuilder.setGenre(result.getGenre())
                    .setYear(result.getYear())
                    .setName(result.getTitle())
                    .setRuntime(result.getRuntime())
                    .setDescription(result.getPlot())
                    .setId(id)
                    .setActors(result.getActors())
                    .setAwards(result.getAwards())
                    .setDirector(result.getDirector())
                    .setImdbScore(result.getImdbRating())
                    .setMetascore(result.getMetascore());
            movie = movieBuilder.build();
        } catch (OMDBException e) {
            Log.e(String.valueOf(context.getResources().getText(R.string.log_omdb_api)), e
                    .toString());
        }
        return movie;
    }

    public int getMovieStats() {
        Log.d(String.valueOf(context.getResources().getText(R.string.log_stats)), String.valueOf
                (context.getResources().getText(R.string.log_stats_start)));
        Document imdbStats;
        Elements numberOfMovies = new Elements();
        try {
            imdbStats = Jsoup.connect(String.valueOf(context.getResources().getText(R.string
                    .imdb_stats))).get();
            numberOfMovies = imdbStats.select(String.valueOf(context.getResources().getText(R
                    .string.imdb_css_selector))); //that's a magic, they don't have normal api
            // for this
        } catch (IOException e) {
            e.printStackTrace();
        }
        int movieNumber = Integer.parseInt(numberOfMovies.get(28).text().replace(",", ""));
        Log.d(String.valueOf(context.getResources().getText(R.string.log_stats)), String.valueOf
                (movieNumber));
        return movieNumber;
    }

}

