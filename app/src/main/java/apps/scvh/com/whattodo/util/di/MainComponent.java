package apps.scvh.com.whattodo.util.di;

import apps.scvh.com.whattodo.ui.activity.MovieRolled;
import dagger.Component;

@Component(modules = {ContextSource.class, DaggerImdbProvider.class})
public interface MainComponent {
    void inject(MovieRolled movieRolled);
}
