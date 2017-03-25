package apps.scvh.com.whattodo.util.imdbApi;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * RUS этот класс отвечает за получение  рандомного фильма из списка фильмов
 * ENG this class is getting one random movie from set of movies
 */
public class ImdbRandomMoviePicker {


    /**
     * ENG this one is counting the "score" of movie
     * RUS этот метод считает бал фильма
     * @param movie the movie
     * @return the score of movie
     */
    private double countScore(Movie movie) {
        Random random = new Random();
        double randomScore = random.nextInt(100);
        return randomScore * movie.getImdbScore() * movie.getMetacriticScore();
    }


    /**
     * ENG picks the best movie from set
     * RUS выбирает лучшее кинцо из списка
     * @param movies set of movies
     * @return the best movie
     */
    private Movie pickBestMovie(HashSet<Movie> movies) {
        Iterator<Movie> moviesIterator = movies.iterator();
        Movie nextMovie;
        while (moviesIterator.hasNext()) {
            nextMovie = moviesIterator.next();
            countScore(nextMovie);
        }
        Movie finalMovie = Collections.max(movies);
        return finalMovie;
    }

    public Observable<Movie> getBestMovieObservable(final HashSet<Movie> movies) {
        return Observable.defer(new Callable<ObservableSource<Movie>>() {
            @Override
            public ObservableSource<Movie> call() throws Exception {
                return Observable.just(pickBestMovie(movies));
            }
        });
    }
}
