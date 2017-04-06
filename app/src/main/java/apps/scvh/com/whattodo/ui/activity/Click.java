package apps.scvh.com.whattodo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jakewharton.rxbinding2.view.RxView;

import apps.scvh.com.whattodo.R;

public class Click extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        RxView.clicks(findViewById(R.id.push_button)).subscribe(t -> {
            Intent showMovie = new Intent(this, MovieRolled.class);
            startActivity(showMovie);
        });
    }

}
