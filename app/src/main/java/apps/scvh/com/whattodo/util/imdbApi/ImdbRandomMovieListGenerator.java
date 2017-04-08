package apps.scvh.com.whattodo.util.imdbApi;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.IgnoringHelper;
import apps.scvh.com.whattodo.util.essences.Movie;


/**
 * RUS Метод отвечает за генерацию рандомного списка фильмов
 * ENG This method is randomly generating set of movies
 */
public class ImdbRandomMovieListGenerator {

    private Context context;
    private ImdbWorker imdbWorker;
    private IgnoringHelper ignoringHelper;

    public ImdbRandomMovieListGenerator(Context context, ImdbWorker imdbWorker, IgnoringHelper
            ignoringHelper) {
        this.context = context;
        this.imdbWorker = imdbWorker;
        this.ignoringHelper = ignoringHelper;
    }

    /**
     * ENG This one is converting set of id's to set of movies
     * RUS Данный метод принимает сет идшок и делает из них сет фильмов
     * @param idSet id of movies
     * @return the set of movies
     */
    public HashSet<Movie> getListOfMovies(HashSet<Integer> idSet, int maxId) {
        Iterator<Integer> movieListIterator = idSet.iterator();
        HashSet<Movie> movies = new HashSet<>();
        Movie movie;
        while (movieListIterator.hasNext()) {
            movie = imdbWorker.getMovie(movieListIterator.next());
            while (movie.getName() == null || ignoringHelper.isIgnored(movie)) {
                Log.e(String.valueOf(context.getResources().getString(R.string.log_omdb_api)),
                        String.valueOf(context.getResources().getString(R.string
                                .log_null_movie)));
                movie = imdbWorker.getMovie(fallbackMovie(maxId));
            }
            movies.add(movie);
        }
        return movies;
    }

    /**
     * ENG this one is generating random list of random id's
     * RUS этот метод генерит список рандомных idшок фильмов
     * @param length the length of set of id's
     * @param maxId  the current max id on imdb
     * @return the list of random id
     */
    public HashSet<Integer> getListOfRandomId(int length, int maxId) {
        Random random = new Random();
        HashSet<Integer> randomIdNumbers = new HashSet<>();
        int nextId; //this instead of int
        while (randomIdNumbers.size() < length) {
            nextId = random.nextInt(maxId) + 1;
            if (randomIdNumbers.contains(nextId)) {
                Log.i(context.getResources().getString(R.string.log_id_generation), context
                        .getResources().getString(R.string.log_id_generation_same_id));
            } else {
                randomIdNumbers.add(nextId);
            }
        }
        return randomIdNumbers;
    }

    /**
     * ENG this one is used if there is no movie on imdb with such id or if any error is occured
     * RUS этот метод используется когда мувика с id не существует на имдь
     * @param maxId the max id on imdb
     * @return the id of movie
     */
    private int fallbackMovie(int maxId) {
        Random random = new Random();
        int newId = random.nextInt(maxId) + 1;
        Log.d(String.valueOf(context.getResources().getString(R.string.log_fallback_id)), String
                .valueOf(newId));
        return newId;
    }

}
