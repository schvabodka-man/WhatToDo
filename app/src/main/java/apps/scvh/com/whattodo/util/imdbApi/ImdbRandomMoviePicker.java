package apps.scvh.com.whattodo.util.imdbApi;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import apps.scvh.com.whattodo.util.essences.MovieConverted;

/**
 * RUS этот класс отвечает за получение  рандомного фильма из списка фильмов
 * ENG this class is getting one random movie from set of movies
 */
public class ImdbRandomMoviePicker {


    /**
     * ENG this one is counting the "score" of movieConverted
     * RUS этот метод считает бал фильма
     * @param movieConverted the movieConverted
     * @return the score of movieConverted
     */
    private double countScore(MovieConverted movieConverted) {
        Random random = new Random();
        double randomScore = random.nextInt(100);
        return randomScore * movieConverted.getImdbScore() * movieConverted.getMetacriticScore();
    }


    /**
     * ENG picks the best movie from set
     * RUS выбирает лучшее кинцо из списка
     * @param movies set of movies
     * @return the best movie
     */
    public MovieConverted pickBestMovie(HashSet<MovieConverted> movies) {
        Iterator<MovieConverted> moviesIterator = movies.iterator();
        MovieConverted nextMovieConverted;
        while (moviesIterator.hasNext()) {
            nextMovieConverted = moviesIterator.next();
            countScore(nextMovieConverted);
        }
        MovieConverted finalMovieConverted = Collections.max(movies);
        return finalMovieConverted;
    }

}
