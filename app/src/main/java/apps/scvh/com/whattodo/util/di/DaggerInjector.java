package apps.scvh.com.whattodo.util.di;

import android.content.Context;


/**
 * ENG simple helper for DI
 * RUS просто класс хелпер для внедрения зависимостей
 */
public class DaggerInjector {

    public MainComponent getComponent() {
        return component;
    }

    private MainComponent component;

    public DaggerInjector(Context context) {
        component = DaggerMainComponent.builder().daggerProvider(new DaggerProvider(context)
        ).build();
    }
}
