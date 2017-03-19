package apps.scvh.com.whattodo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import apps.scvh.com.whattodo.R;

public class Click extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        findViewById(R.id.push_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.push_button) {
            Intent showMovie = new Intent(this, MovieRolled.class);
            startActivity(showMovie);
        }
    }
}
