package apps.scvh.com.whattodo.util;

import java.util.List;

import apps.scvh.com.whattodo.util.essences.Movie;

public class IgnoringHelper {

    public void ignoreMovie(Movie movie) {
        movie.save();
    }

    public void unignoreMovie(Movie movie) {
        movie.delete();
    }

    public boolean isIgnored(Movie movie) {
        List<Movie> result = Movie.find(Movie.class, "imdb_id = ?", String.valueOf(movie
                .getImdbId()));
        return !result.isEmpty();
    }
}
