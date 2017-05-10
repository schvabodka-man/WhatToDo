package util.workers;


import android.app.Application;
import android.preference.PreferenceManager;

import junit.framework.Assert;

import org.junit.Test;

import apps.scvh.com.whattodo.util.essences.MovieBuilder;
import apps.scvh.com.whattodo.util.workers.Filterer;

public class FiltererTest extends Application {

    private Filterer filterer;

    public FiltererTest() {
        filterer = new Filterer(PreferenceManager.getDefaultSharedPreferences(this), this);
    }

    @Test
    public void testIgnoringByPic() {
        MovieBuilder builder = new MovieBuilder();
        builder.setPicture("N/A");
        boolean isFiltered = filterer.isFiltered(builder.build());
        Assert.assertEquals(true, isFiltered);
    }

    @Test
    public void testIgnoringByAward() {
        MovieBuilder builder = new MovieBuilder();
        builder.setAwards("N/A");
        boolean isFiltered = filterer.isFiltered(builder.build());
        Assert.assertEquals(true, isFiltered);
    }

    @Test
    public void testIgnoringByScore() {
        MovieBuilder builder = new MovieBuilder();
        builder.setMetascore(0);
        boolean isFiltered = filterer.isFiltered(builder.build());
        Assert.assertEquals(true, isFiltered);
    }

}
