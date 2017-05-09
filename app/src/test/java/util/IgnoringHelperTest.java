package util;


import android.app.Application;

import com.omertron.omdbapi.OmdbApi;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.Callable;

import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMovieListGenerator;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;
import apps.scvh.com.whattodo.util.workers.IgnoringHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;

public class IgnoringHelperTest extends Application {

    //this is without DI
    private IgnoringHelper helper;
    private ImdbRandomMovieListGenerator listGenerator;
    private ImdbWorker worker;


    public IgnoringHelperTest() {
        helper = new IgnoringHelper();
        worker = new ImdbWorker(this, new OmdbApi());
        listGenerator = new ImdbRandomMovieListGenerator(this, worker, helper);
    }

    @Test
    public void testIgnoring() {
        Observable.defer(new Callable<ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> call() throws Exception {
                HashSet<Integer> testId = new HashSet<>();
                testId.add(137523);
                Movie tester = new Movie();
                tester.setImdbId(137523);
                tester.save();
                HashSet<Movie> nonIgnored = listGenerator.getListOfMovies(testId, worker
                        .getMovieStats());
                boolean isIgnored = helper.isIgnored(nonIgnored.iterator().next());
                return Observable.just(isIgnored);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(isIgnored -> Assert.assertEquals(Boolean
                .FALSE, isIgnored));
    }
}
