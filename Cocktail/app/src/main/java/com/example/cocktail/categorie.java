package com.example.cocktail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class categorie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Top Bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_categorie);
    }
}
