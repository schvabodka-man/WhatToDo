package apps.scvh.com.whattodo.util.di;

import javax.inject.Singleton;

import apps.scvh.com.whattodo.ui.IgnoredAdapter;
import apps.scvh.com.whattodo.ui.activity.IgnoredMovies;
import apps.scvh.com.whattodo.ui.activity.MovieRolled;
import dagger.Component;

/**
 * ENG interface for DI. Injecting with inject
 * RUS интерфейс нужный для инжектов. метод inject собственно инжектит
 */
@Singleton
@Component(modules = {DaggerProvider.class})
public interface MainComponent {

    void inject(MovieRolled movieRolled);

    void inject(IgnoredMovies ignoredMovies);

    void inject(IgnoredAdapter ignoredAdapter);

}
