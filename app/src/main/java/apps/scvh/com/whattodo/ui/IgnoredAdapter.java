package apps.scvh.com.whattodo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.Movie;
import apps.scvh.com.whattodo.util.handlers.UIHandler;


public class IgnoredAdapter extends ArrayAdapter<Movie> {

    @Inject
    @Named("UIHandler")
    UIHandler handler;

    public IgnoredAdapter(Context context, List<Movie> movieList) {
        super(context, 0, movieList);
        DaggerInjector injector = new DaggerInjector(context);
        injector.getComponent().inject(this);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ignored_movie,
                    parent, false);
        }
        //No DI here cause it's dynamic stuff
        TextView title = (TextView) convertView.findViewById(R.id.ignored_title);
        TextView director = (TextView) convertView.findViewById(R.id.ignored_director);
        final ImageView cover = (ImageView) convertView.findViewById(R.id.ignored_covering);
        title.setText(movie.getName());
        director.setText(movie.getDirector());
        if (movie.getPictureId().equals("N/A")) {
            cover.setBackground(getContext().getDrawable(R.mipmap.placeholder));
        } else {
            handler.getPicture(movie.getPictureId()).subscribe(drawable -> {
                cover.setBackground(drawable);
            });
        }
        return convertView;
    }
}
