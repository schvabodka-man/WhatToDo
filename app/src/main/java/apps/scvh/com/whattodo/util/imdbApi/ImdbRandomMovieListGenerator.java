package apps.scvh.com.whattodo.util.imdbApi;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.essences.MovieConverted;
import apps.scvh.com.whattodo.util.workers.Filterer;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;


/**
 * RUS Метод отвечает за генерацию рандомного списка фильмов
 * ENG This method is randomly generating set of movies
 */
public class ImdbRandomMovieListGenerator {

    private Context context;
    private ImdbWorker imdbWorker;
    private IgnoringHelper ignoringHelper;
    private Filterer filterer;

    public ImdbRandomMovieListGenerator(Context context, ImdbWorker imdbWorker, IgnoringHelper
            ignoringHelper, Filterer filterer) {
        this.context = context;
        this.imdbWorker = imdbWorker;
        this.ignoringHelper = ignoringHelper;
        this.filterer = filterer;
    }

    /**
     * ENG This one is converting set of id's to set of movies
     * RUS Данный метод принимает сет идшок и делает из них сет фильмов
     * @param idSet id of movies
     * @return the set of movies
     */
    public HashSet<MovieConverted> getListOfMovies(HashSet<Integer> idSet, int maxId) {
        Iterator<Integer> movieListIterator = idSet.iterator();
        HashSet<MovieConverted> movies = new HashSet<>();
        MovieConverted movieConverted;
        while (movieListIterator.hasNext()) {
            movieConverted = imdbWorker.getMovie(movieListIterator.next());
            while (movieConverted.getName() == null || ignoringHelper.isIgnored(movieConverted) || movieConverted.getName()
                    .contains(context.getString(R.string.episode_ignored)) || movieConverted.getName()
                    .contains(context.getString(R.string.dupe_ignored)) || movieConverted.getName()
                    .equals(context.getString(R.string.imdb_na)) || filterer.isFiltered
                    (movieConverted)) {
                Log.e(String.valueOf(context.getResources().getString(R.string.log_omdb_api)),
                        String.valueOf(context.getResources().getString(R.string
                                .log_null_movie)));
                movieConverted = imdbWorker.getMovie(fallbackMovie(maxId));
            }
            movies.add(movieConverted);
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
