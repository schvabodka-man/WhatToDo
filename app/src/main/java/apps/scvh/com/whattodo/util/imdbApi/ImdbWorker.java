package apps.scvh.com.whattodo.util.imdbApi;


import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.essences.MovieBuilder;
import apps.scvh.com.whattodo.util.essences.MovieConverted;
import apps.scvh.com.whattodo.util.omdbImplementation.Movie;
import apps.scvh.com.whattodo.util.omdbImplementation.OmdbAPIRetriever;

/**
 * ENG this class is getting data from omdb api
 * RUS этот класс получает данные из апи
 */
public class ImdbWorker {

    private Context context;
    private OmdbAPIRetriever omdbApi;

    public ImdbWorker(Context context, OmdbAPIRetriever omdbApi) {
        this.context = context;
        this.omdbApi = omdbApi;
    }

    /**
     * ENG this one get's movie data by id
     * RUS этот метод получает фильм по id'шке
     * @param id the movie id
     * @return the movie
     */
    MovieConverted getMovie(int id) {
        MovieBuilder movieBuilder = new MovieBuilder();
        String withLeadingZeroes = String.format(Locale.US, context.getResources().getString(R
                .string.imdb_leading_zeroes), id); //This is for zeroes at the beginning of id
        Movie result = omdbApi.retrieveMovie(String.valueOf
                (context.getResources().getText(R.string.imdb_prefix)) + withLeadingZeroes, "22189408");
        movieBuilder.setGenre(result.getGenre())
                .setYear(result.getReleased())
                .setName(result.getTitle())
                .setRuntime(result.getRuntime())
                .setDescription(result.getPlot())
                .setId(id)
                .setActors(result.getActors())
                .setAwards(result.getAwards())
                .setDirector(result.getDirector())
                .setPicture(result.getPoster());
        if (String.valueOf(result.getImdbRating()).equals(context.getResources().getString(R.string.imdb_na))) {
            Log.d(String.valueOf(context.getResources().getText(R.string.log_omdb_api)),
                    String.valueOf(context.getResources().getText(R.string
                            .log_omdb_unknown_imdb_rating)));
            movieBuilder.setImdbScore(1);
        } else {
            Log.i(String.valueOf(context.getResources().getText(R.string.log_omdb_api)),
                    String.valueOf(context.getResources().getText(R.string.log_imdb_score)) +
                            result.getImdbRating());
            movieBuilder.setImdbScore(Double.parseDouble(result.getImdbRating()));
        }
        if (String.valueOf(result.getMetascore()).equals(context.getResources().getString(R.string.imdb_na))) {
            movieBuilder.setMetascore(1);
        } else {
            movieBuilder.setMetascore(Integer.parseInt(result.getMetascore()));
        }
        return movieBuilder.build();
    }

    /**
     * ENG this method is getting maximum idm id by parsing html(there are no api for that)
     * RUS этот метод получает максимальную id'шку на имбд путем парсинг хтмла(апи нету)
     * @return the maximum imdb id
     */
    public int getMovieStats() {
        Log.d(String.valueOf(context.getResources().getText(R.string.log_stats)), String.valueOf
                (context.getResources().getText(R.string.log_stats_start)));
        Document imdbStats;
        Elements numberOfMovies = new Elements();
        //Now there goes magic, there are no normal api for getting imdb stats so you need to
        // parse html
        try {
            imdbStats = Jsoup.connect(String.valueOf(context.getResources().getText(R.string
                    .imdb_stats))).get();
            numberOfMovies = imdbStats.select(String.valueOf(context.getResources().getText(R
                    .string.imdb_css_selector)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //there goes a magick number, yep really. If someone's reading this you may know that
        // it's really bad idea, but i still don't have alternative
        int movieNumber = Integer.parseInt(numberOfMovies.get(0).text().replace(",", ""));
        Log.d(String.valueOf(context.getResources().getText(R.string.log_stats)), String.valueOf
                (movieNumber));
        return movieNumber;
    }


}

