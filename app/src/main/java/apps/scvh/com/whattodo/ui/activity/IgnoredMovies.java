package apps.scvh.com.whattodo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.ui.IgnoredAdapter;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.handlers.IgnoringHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IgnoredMovies extends Activity {

    @BindView(R.id.ignored_list)
    ListView list;

    @Inject
    @Named("Ignore")
    IgnoringHelper helper;

    private IgnoredAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignored_movies);
        ButterKnife.bind(this);
        DaggerInjector injector = new DaggerInjector(this);
        injector.getComponent().inject(this);
        init();
    }

    private void init() {
        context = this;
        helper.getListOfIgnoredMovies().subscribe(movies -> {
            adapter = new IgnoredAdapter(context, movies);
            list.setAdapter(adapter);
        });
    }

}
