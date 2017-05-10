package util.imdbApi;


import android.app.Application;
import android.preference.PreferenceManager;

import com.omertron.omdbapi.OmdbApi;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashSet;

import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import apps.scvh.com.whattodo.util.workers.Filterer;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;

public class ImdbRandomMovieListGeneratorTest extends Application {

    //this is without DI
    private IgnoringHelper helper;
    private ImdbRandomMovieListGenerator listGenerator;
    private ImdbWorker worker;
    private Filterer filterer;


    public ImdbRandomMovieListGeneratorTest() {
        helper = new IgnoringHelper();
        worker = new ImdbWorker(this, new OmdbApi());
        filterer = new Filterer(PreferenceManager.getDefaultSharedPreferences(this), this);
        listGenerator = new ImdbRandomMovieListGenerator(this, worker, helper, filterer);
    }

    @Test
    public void ignoringBlankMovies() {
        HashSet<Integer> set = new HashSet<>();
        set.add(1591847);
        Movie movie = listGenerator.getListOfMovies(set, worker.getMovieStats()).iterator().next();
        Assert.assertEquals(!movie.getName().equals("Episode #7.15"), false);
    }
}
