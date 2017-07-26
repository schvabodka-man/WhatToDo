package apps.scvh.com.whattodo.util.essences;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class MovieConverted extends SugarRecord implements Comparable<MovieConverted> {

    private int imdbId;
    private String name;
    @Ignore
    private String year;
    @Ignore
    private double imdbScore;
    @Ignore
    private int metacriticScore;
    @Ignore
    private String description;
    private String director;
    @Ignore
    private String awards;
    @Ignore
    private String actors;
    @Ignore
    private String runtime;
    @Ignore
    private String genre;
    private String pictureId;

    private double finalScore;

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public int getMetacriticScore() {
        return metacriticScore;
    }

    public void setMetacriticScore(int metacriticScore) {
        this.metacriticScore = metacriticScore;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String toString() {
        String objectToString = name + " " + year + " " + " " + description;
        return objectToString;
    }

    @Override
    public int compareTo(MovieConverted o) {
        if (o.getFinalScore() > this.finalScore) {
            return 1;
        } else if (o.getFinalScore() < this.finalScore) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().toString().equals("MovieConverted")) {
            MovieConverted compare = (MovieConverted) obj;
            if (compare.getImdbId() == this.imdbId) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
