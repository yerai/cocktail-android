package com.example.cocktail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    private ArrayList<cocktail> FavoritesCocktailArrayList = new ArrayList<cocktail>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FavoritesCocktailArrayList.clear();
        FavoritesCocktailArrayList = MainActivity.getFavorites();

        // Hide Top Bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_favorites);

        // Navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(Favorites.this,MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_favorites:
                        Intent b = new Intent(Favorites.this,Favorites.class);
                        startActivity(b);

                        break;
                }
                return false;
            }
        });

        //Fonts
        final Typeface main_title = getResources().getFont(R.font.gothamblack);
        final Typeface sub_sub_title = getResources().getFont(R.font.gothammedium);
        final Typeface text = getResources().getFont(R.font.montserratrregular);

        // Set titles
        ((TextView)findViewById(R.id.favorites_title)).setTypeface(main_title);
        ((TextView)findViewById(R.id.favorites_title)).setTextColor(Color.parseColor("#343035"));

        ((LinearLayout) findViewById(R.id.first_column)).removeAllViews();
        ((LinearLayout) findViewById(R.id.second_column)).removeAllViews();

        for(int i= 0; i<FavoritesCocktailArrayList.size(); i++){
            /* Linear Layout */
            final LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.VERTICAL);

            /* CardView */
            CardView cv = new CardView(this);
            cv.setCardElevation(DPS(0));
            cv.setRadius(DPS(5));
            CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(170),DPS(170)));
            params1.setMargins(DPS(20),DPS(10),DPS(20),DPS(0));
            cv.setLayoutParams(params1);

            /* Image View */
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            new MainActivity.DownloadImageTask(iv).execute(FavoritesCocktailArrayList.get(i).image);

            /* Linear Layout */
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setLayoutParams(params);
            ll2.setOrientation(LinearLayout.HORIZONTAL);

            /* Text View */
            TextView tv = new TextView(this);
            tv.setTypeface(sub_sub_title);
            tv.setText(FavoritesCocktailArrayList.get(i).name);
            tv.setTextSize(DPS(7));
            tv.setTextColor(Color.parseColor("#323031"));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params3.setMargins(DPS(10),DPS(5),0,0);
            tv.setLayoutParams(params3);

            /* Image View */
            final ImageView iv2 = new ImageView(this);
            iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams((new LinearLayout.LayoutParams(55,55)));
            params4.setMargins(DPS(23),DPS(5),0,0);
            iv2.setLayoutParams(params4);
            iv2.setBackgroundResource(R.drawable.heart_fill);

            final int finalI = i;
            iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv2.setBackgroundResource(R.drawable.heart_border);
                    MainActivity.removeFavorite(finalI);
                }
            });


            cv.addView(iv);
            ll2.addView(iv2);
            ll2.addView(tv);
            ll.addView(cv);
            ll.addView(ll2);

            if(i%2==0){
                ((LinearLayout) findViewById(R.id.first_column)).addView(ll);
            }else{
                ((LinearLayout) findViewById(R.id.second_column)).addView(ll);
            }

        }



    }

    // DPS to Pixels Function
    private int DPS(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}