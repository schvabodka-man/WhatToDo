package apps.scvh.com.whattodo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import apps.scvh.com.whattodo.R;

public class Click extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        RxView.clicks(findViewById(R.id.push_button)).subscribe(t -> {
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
        });
    }

}
