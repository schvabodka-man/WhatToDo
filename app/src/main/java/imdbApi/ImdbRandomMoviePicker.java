package imdbApi;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import essences.Movie;

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
    public double countScore(Movie movie) {
        Random random = new Random();
        double randomScore = random.nextInt(100);
        return randomScore * movie.getMetacriticScore();
    }

    /**
     * ENG picks the best movie from set
     * RUS выбирает лучшее кинцо из списка
     * @param movies set of movies
     * @return the best movie
     */
    public Movie pickBestMovie(HashSet<Movie> movies) {
        Iterator<Movie> moviesIterator = movies.iterator();
        Movie countable;
        while (moviesIterator.hasNext()) {
            countable = moviesIterator.next();
            countScore(countable);
        }
        return null;
    }
}
