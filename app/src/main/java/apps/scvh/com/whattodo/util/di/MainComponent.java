package apps.scvh.com.whattodo.util.di;

import javax.inject.Singleton;

import apps.scvh.com.whattodo.ui.activity.MovieRolled;
import dagger.Component;

@Singleton
@Component(modules = {DaggerProvider.class})
public interface MainComponent {

    void inject(MovieRolled movieRolled);

}
