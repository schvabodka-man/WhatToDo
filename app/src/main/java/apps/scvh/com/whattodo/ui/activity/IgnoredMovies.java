package apps.scvh.com.whattodo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.ui.IgnoredAdapter;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.Movie;
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
    private ArrayList<Movie> moviesFromAdapter; //this one for UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignored_movies);
        ButterKnife.bind(this);
        DaggerInjector injector = new DaggerInjector(this);
        injector.getComponent().inject(this);
        init();
        setOnClickListeners();
    }

    private void init() {
        context = this;
        helper.getListOfIgnoredMovies().subscribe(movies -> {
            moviesFromAdapter = (ArrayList<Movie>) movies;
            adapter = new IgnoredAdapter(context, moviesFromAdapter);
            list.setAdapter(adapter);
        });
    }

    private void setOnClickListeners() {
        list.setOnItemClickListener((parent, view, position, id) -> {
            PopupMenu popupMenu = new PopupMenu(IgnoredMovies.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.ignored_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.unignore:
                        return removeMovie(position);
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
    }

    private boolean removeMovie(int positionOfMovie) {
        helper.unignoreMovie(moviesFromAdapter.get(positionOfMovie));
        moviesFromAdapter.remove(positionOfMovie);
        adapter.notifyDataSetChanged();
        return true;
    }
}
