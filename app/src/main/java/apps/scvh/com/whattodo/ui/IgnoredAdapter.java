package apps.scvh.com.whattodo.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.di.DaggerInjector;
import apps.scvh.com.whattodo.util.essences.MovieConverted;
import apps.scvh.com.whattodo.util.workers.UIHandler;


/**
 * ENG adapter need for displaying ignored movies
 * RUS адаптер нужный мне чтобы показать заигноренные кинчики
 */
public class IgnoredAdapter extends ArrayAdapter<MovieConverted> {

    @Inject
    @Named("UIHandler")
    UIHandler handler;

    public IgnoredAdapter(Context context, ArrayList<MovieConverted> movieConvertedList) {
        super(context, 0, movieConvertedList);
        DaggerInjector injector = new DaggerInjector(context);
        injector.getComponent().inject(this);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieConverted movieConverted = getItem(position);
        if (movieConverted.getPictureId().equals("N/A")) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ignored_movie_no_cover,
                    parent, false);
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ignored_movie,
                    parent, false);
            ImageView cover = (ImageView) convertView.findViewById(R.id.ignored_covering);
            handler.getPicture(movieConverted.getPictureId()).subscribe(cover::setBackground);
        }
        TextView title = (TextView) convertView.findViewById(R.id.ignored_title);
        TextView director = (TextView) convertView.findViewById(R.id.ignored_director);
        title.setText(movieConverted.getName());
        director.setText(movieConverted.getDirector());
        return convertView;
    }

}

