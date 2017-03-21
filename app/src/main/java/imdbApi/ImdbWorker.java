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

/**
 * ENG this class is getting data from omdb api
 * RUS этот класс получает данные из апи
 */
public class ImdbWorker {

    private Context context;
    private OmdbApi omdbApi;

    public ImdbWorker(Context context, OmdbApi omdbApi) {
        this.context = context;
        this.omdbApi = omdbApi;
    }

    /**
     * ENG this one get's movie data by id
     * RUS этот метод получает фильм по id'шке
     * @param id the movie id
     * @return the movie
     */
    public Movie getMovie(int id) {
        MovieBuilder movieBuilder = new MovieBuilder();
        Movie movie = new Movie();
        String withLeadingZeroes = String.format(Locale.US, context.getResources().getString(R
                .string.imdb_leading_zeroes), id); //This is for zeroes at the beginning of id
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
                    .setMetascore(result.getMetascore());
            if (result.getImdbRating().equals(context.getResources().getString(R.string.imdb_na))) {
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
            movie = movieBuilder.build();
        } catch (OMDBException e) {
            Log.e(String.valueOf(context.getResources().getText(R.string.log_omdb_api)), e
                    .toString());
        }
        return movie;
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
        int movieNumber = Integer.parseInt(numberOfMovies.get(28).text().replace(",", ""));
        Log.d(String.valueOf(context.getResources().getText(R.string.log_stats)), String.valueOf
                (movieNumber));
        return movieNumber;
    }

}

