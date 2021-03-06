package com.example.maps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FavorisActivity extends AppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // To Add the favoris icon on action bar
        //getSupportActionBar();
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_baseline_star_24);

        setContentView(R.layout.activity_favoris);
    }

    public void goToMap(View view) {
        Intent intentMap = new Intent(this, MapsActivity.class);
        intentMap.putExtra("Map", true);
        startActivity(intentMap);
    }
}