package util.imdbApi;


import android.app.Application;
import android.preference.PreferenceManager;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashSet;

import apps.scvh.com.whattodo.util.essences.MovieConverted;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import apps.scvh.com.whattodo.util.omdbImplementation.OmdbAPIRetriever;
import apps.scvh.com.whattodo.util.workers.Filterer;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;

public class ImdbRandomMovieConvertedListGeneratorTest extends Application {

    //this is without DI
    private IgnoringHelper helper;
    private ImdbRandomMovieListGenerator listGenerator;
    private ImdbWorker worker;
    private Filterer filterer;


    public ImdbRandomMovieConvertedListGeneratorTest() {
        helper = new IgnoringHelper();
        worker = new ImdbWorker(this, new OmdbAPIRetriever());
        filterer = new Filterer(PreferenceManager.getDefaultSharedPreferences(this), this);
        listGenerator = new ImdbRandomMovieListGenerator(this, worker, helper, filterer);
    }

    @Test
    public void ignoringBlankMovies() {
        HashSet<Integer> set = new HashSet<>();
        set.add(1591847);
        MovieConverted movieConverted = listGenerator.getListOfMovies(set, worker.getMovieStats()).iterator().next();
        Assert.assertEquals(!movieConverted.getName().equals("Episode #7.15"), false);
    }
}
