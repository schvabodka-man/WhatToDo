package apps.scvh.com.whattodo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.orm.SugarContext;

import apps.scvh.com.whattodo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Click extends FragmentActivity {

    @BindView(R.id.push_button)
    Button pushButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        ButterKnife.bind(this);
        SugarContext.init(this);
        initClickListeners();
    }

    private void initClickListeners() {
        RxView.clicks(pushButton).subscribe(t -> {
            generateNewMovie();
        });
        RxView.longClicks(pushButton).subscribe(t -> {
            PopupMenu popupMenu = new PopupMenu(Click.this, pushButton);
            popupMenu.getMenuInflater().inflate(R.menu.long_click_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.ignored_movies_list_long_click:
                        Intent intent = new Intent(this, IgnoredMovies.class);
                        startActivity(intent);
                        return true;
                    case R.id.settings_long_click:
                        Intent settings = new Intent(this, Settings.class);
                        startActivity(settings);
                        return true;
                    case R.id.roll_movie_long_click:
                        generateNewMovie();
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
        });
    }

    private void generateNewMovie() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isInternet = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isInternet) {
            Intent showMovie = new Intent(this, MovieRolled.class);
            startActivity(showMovie);
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
}
