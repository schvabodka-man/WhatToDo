package apps.scvh.com.whattodo.util.di;


import android.app.Application;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * ENG class i need to get app context
 * RUS класс нужный мне для получения контекста
 */
@Module
public class ContextSource extends Application {

    @Provides
    @Named("context")
    Context provideContext() {
        return this;
    }

}
