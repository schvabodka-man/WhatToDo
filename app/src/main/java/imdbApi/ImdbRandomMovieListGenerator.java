package imdbApi;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import apps.scvh.com.whattodo.R;
import essences.Movie;

public class ImdbRandomMovieListGenerator {

    private Context context;
    private ImdbWorker imdbWorker;

    public ImdbRandomMovieListGenerator(Context context, ImdbWorker imdbWorker) {
        this.context = context;
        this.imdbWorker = imdbWorker;
    }

    public HashSet<Movie> getListOfMovies(HashSet<Integer> idSet) {
        Iterator<Integer> movieListIterator = idSet.iterator();
        HashSet<Movie> movies = new HashSet<>();
        if (movieListIterator.hasNext()) {
            movies.add(imdbWorker.getMovie(movieListIterator.next()));
        }
        return movies;
    }

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
}
