package apps.scvh.com.whattodo.util.di;

import android.content.Context;


public class DaggerInjector {

    public MainComponent getComponent() {
        return component;
    }

    private MainComponent component;

    public DaggerInjector(Context context) {
        component = DaggerMainComponent.builder().daggerImdbProvider(new DaggerImdbProvider
                (context)).build();
    }
}
