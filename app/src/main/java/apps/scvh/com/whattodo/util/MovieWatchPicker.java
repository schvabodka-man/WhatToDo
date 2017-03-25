package apps.scvh.com.whattodo.util;


import java.util.HashSet;

import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;

/**
 * ENG Simple decorator to hack all together
 * RUS просто декоратор чтобы просто получить все нужные приколы
 */
public class MovieWatchPicker {

    private ImdbRandomMoviePicker randomMoviePicker;
    private ImdbWorker worker;
    private ImdbRandomMovieListGenerator listGenerator;

    private final int NUMBER_OF_MOVIES = 8;

    public MovieWatchPicker(ImdbRandomMoviePicker randomMoviePicker, ImdbWorker worker,
                            ImdbRandomMovieListGenerator listGenerator) {
        this.randomMoviePicker = randomMoviePicker;
        this.worker = worker;
        this.listGenerator = listGenerator;
    }

    public Movie getMovieToWatch() {
        int maxId = worker.getMovieStats();
        HashSet<Movie> randomMovies = listGenerator.getListOfMovies(listGenerator
                .getListOfRandomId(NUMBER_OF_MOVIES, maxId), maxId);
        return randomMoviePicker.pickBestMovie(randomMovies);
    }
}
