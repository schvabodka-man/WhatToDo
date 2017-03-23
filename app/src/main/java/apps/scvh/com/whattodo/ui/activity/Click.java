package apps.scvh.com.whattodo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.omertron.omdbapi.OmdbApi;

import apps.scvh.com.whattodo.R;
import apps.scvh.com.whattodo.util.imdbApi.ImdbRandomMoviePicker;
import apps.scvh.com.whattodo.util.imdbApi.ImdbWorker;

public class Click extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        findViewById(R.id.push_button).setOnClickListener(this);
        ImdbRandomMoviePicker imdb = new ImdbRandomMoviePicker(new ImdbWorker(this, new OmdbApi()));
        imdb.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.push_button) {
            Intent showMovie = new Intent(this, MovieRolled.class);
            startActivity(showMovie);
        }
    }
}
