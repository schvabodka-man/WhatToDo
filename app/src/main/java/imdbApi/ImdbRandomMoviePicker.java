package imdbApi;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import essences.Movie;

public class ImdbRandomMoviePicker {

    public double countScore(Movie movie) {
        Random random = new Random();
        double randomScore = random.nextInt(100);
        double finalScore = randomScore * movie.getImdbScore() * movie.getMetacriticScore();
        return finalScore;
    }

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
