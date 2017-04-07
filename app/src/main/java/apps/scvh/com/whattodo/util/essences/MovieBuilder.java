package apps.scvh.com.whattodo.util.essences;

public class MovieBuilder {

    private Movie movie;

    public MovieBuilder() {
        movie = new Movie();
    }

    public MovieBuilder setName(String name) {
        movie.setName(name);
        return this;
    }

    public Movie build() {
        return movie;
    }

    public MovieBuilder setYear(String year) {
        movie.setYear(year);
        return this;
    }

    public MovieBuilder setDescription(String desc) {
        movie.setDescription(desc);
        return this;
    }

    public MovieBuilder setId(int id) {
        movie.setImdbId(id);
        return this;
    }

    public MovieBuilder setImdbScore(double imdbScore) {
        movie.setImdbScore(imdbScore);
        return this;
    }

    public MovieBuilder setMetascore(int metacriticScore) {
        movie.setMetacriticScore(metacriticScore);
        return this;
    }

    public MovieBuilder setDirector(String director) {
        movie.setDirector(director);
        return this;
    }

    public MovieBuilder setAwards(String awards) {
        movie.setAwards(awards);
        return this;
    }

    public MovieBuilder setActors(String actors) {
        movie.setActors(actors);
        return this;
    }

    public MovieBuilder setRuntime(String runtime) {
        movie.setRuntime(runtime);
        return this;
    }

    public MovieBuilder setGenre(String genre) {
        movie.setGenre(genre);
        return this;
    }

    public MovieBuilder setPicture(String picture) {
        movie.setPictureId(picture);
        return this;
    }
}
