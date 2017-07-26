package apps.scvh.com.whattodo.util.essences;

public class MovieBuilder {

    private MovieConverted movieConverted;

    public MovieBuilder() {
        movieConverted = new MovieConverted();
    }

    public MovieBuilder setName(String name) {
        movieConverted.setName(name);
        return this;
    }

    public MovieConverted build() {
        return movieConverted;
    }

    public MovieBuilder setYear(String year) {
        movieConverted.setYear(year);
        return this;
    }

    public MovieBuilder setDescription(String desc) {
        movieConverted.setDescription(desc);
        return this;
    }

    public MovieBuilder setId(int id) {
        movieConverted.setImdbId(id);
        return this;
    }

    public MovieBuilder setImdbScore(double imdbScore) {
        movieConverted.setImdbScore(imdbScore);
        return this;
    }

    public MovieBuilder setMetascore(int metacriticScore) {
        movieConverted.setMetacriticScore(metacriticScore);
        return this;
    }

    public MovieBuilder setDirector(String director) {
        movieConverted.setDirector(director);
        return this;
    }

    public MovieBuilder setAwards(String awards) {
        movieConverted.setAwards(awards);
        return this;
    }

    public MovieBuilder setActors(String actors) {
        movieConverted.setActors(actors);
        return this;
    }

    public MovieBuilder setRuntime(String runtime) {
        movieConverted.setRuntime(runtime);
        return this;
    }

    public MovieBuilder setGenre(String genre) {
        movieConverted.setGenre(genre);
        return this;
    }

    public MovieBuilder setPicture(String picture) {
        movieConverted.setPictureId(picture);
        return this;
    }
}
