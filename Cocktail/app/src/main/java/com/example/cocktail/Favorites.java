package com.example.cocktail;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    private ArrayList<cocktail> FavoritesCocktailArrayList = new ArrayList<cocktail>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add Recent Cocktails
        FavoritesCocktailArrayList.add(new cocktail("Bloody Mary", "https://www.thecocktaildb.com/images/media/drink/uyquuu1439906954.jpg"));
        FavoritesCocktailArrayList.add(new cocktail("Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
        FavoritesCocktailArrayList.add(new cocktail("Daiquiri", "https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
        FavoritesCocktailArrayList.add(new cocktail("Whiskey Sour", "https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));


        // Hide Top Bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_favorites);

        //Fonts
        Typeface title = getResources().getFont(R.font.fredoka);
        Typeface title2 = getResources().getFont(R.font.latobold);
        Typeface title3 = getResources().getFont(R.font.latoblack);
        Typeface text = getResources().getFont(R.font.montserratrregular);

        // Set titles
        ((TextView)findViewById(R.id.favorites_title)).setTypeface(title);
        ((TextView)findViewById(R.id.favorites_title)).setTextColor(Color.parseColor("#343035"));


        for(int i= 0; i<FavoritesCocktailArrayList.size(); i++){
            /* Linear Layout */
            LinearLayout ll = new LinearLayout(this);
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
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)));
            new MainActivity.DownloadImageTask(iv).execute(FavoritesCocktailArrayList.get(i).image);

            /* Linear Layout */
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setLayoutParams(params);
            ll2.setOrientation(LinearLayout.HORIZONTAL);

            /* Text View */
            TextView tv = new TextView(this);
            tv.setTypeface(title2);
            tv.setText(FavoritesCocktailArrayList.get(i).name);
            tv.setTextSize(DPS(5));
            tv.setTextColor(Color.parseColor("#323031"));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params3.setMargins(DPS(10),DPS(5),0,0);
            tv.setLayoutParams(params3);

            /* Image View */
            ImageView iv2 = new ImageView(this);
            iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams((new LinearLayout.LayoutParams(80,80)));
            params4.setMargins(DPS(23),DPS(5),0,0);
            iv2.setLayoutParams(params4);
            new MainActivity.DownloadImageTask(iv2).execute("https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678087-heart-512.png");

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
