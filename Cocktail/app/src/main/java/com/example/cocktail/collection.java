package com.example.cocktail;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class collection extends AppCompatActivity {

    private ArrayList<cocktail> FeaturedCocktailArrayList = new ArrayList<cocktail>();

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Top Bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_collection);

        //Fonts
        Typeface title = getResources().getFont(R.font.fredoka);
        Typeface title2 = getResources().getFont(R.font.latobold);
        Typeface title3 = getResources().getFont(R.font.latoblack);
        Typeface text = getResources().getFont(R.font.montserratrregular);


        // Get Title & Photo
        String collectionName = getIntent().getStringExtra("title");
        String collectionPicture = getIntent().getStringExtra("photo");

        if(collectionName.equals("Top 15")){
            FeaturedCocktailArrayList.add(new cocktail("Bloody Mary", "https://www.thecocktaildb.com/images/media/drink/uyquuu1439906954.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11000","Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("Daiquiri", "https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("Whiskey Sour", "https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));
        }else if(collectionName.equals("Christmas Collection")){
            FeaturedCocktailArrayList.add(new cocktail("Daiquiri", "https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("Bloody Mary", "https://www.thecocktaildb.com/images/media/drink/uyquuu1439906954.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("Whiskey Sour", "https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));
        }else{

        }

        /* CardView */
        CardView cv = new CardView(this);
        cv.setCardElevation(DPS(0));
        cv.setRadius(DPS(0));
        CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DPS(300)));
        params1.setMargins(DPS(0),DPS(0),DPS(0),DPS(0));
        cv.setLayoutParams(params1);
        /* ImageView */
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        iv.setLayoutParams(params2);
        new MainActivity.DownloadImageTask(iv).execute(collectionPicture);
        /* Black Background */
        TextView tv = new TextView(this);
        tv.setLayoutParams(params2);
        tv.setBackgroundColor(Color.parseColor("#80343035"));
        tv.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv1 = new TextView(this);
        tv1.setText(collectionName);
        tv1.setLayoutParams(params2);
        tv1.setTextSize(DPS(15));
        tv1.setTypeface(title);
        tv1.setTextColor(Color.parseColor("#ffffff"));
        tv1.setGravity(Gravity.CENTER);
        /* Add components */
        cv.addView(iv);
        cv.addView(tv);
        cv.addView(tv1);
        ((LinearLayout)findViewById(R.id.collection_layout)).addView(cv);

        // Featured List
        for(int i = 0; i<FeaturedCocktailArrayList.size(); i++){
            /* Linear Layout */
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            /* CardView */
            CardView cv1 = new CardView(this);
            cv1.setCardElevation(DPS(0));
            cv1.setRadius(DPS(5));
            CardView.LayoutParams params3 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(90),DPS(90)));
            params3.setMargins(DPS(0),DPS(0),DPS(0),DPS(15));
            cv1.setLayoutParams(params3);

            /* ImageView */
            ImageView iv1 = new ImageView(this);
            iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            iv1.setLayoutParams(params4);
            new MainActivity.DownloadImageTask(iv1).execute(FeaturedCocktailArrayList.get(i).image);

            /* TextView */
            TextView tv2 = new TextView(this);
            tv2.setText(FeaturedCocktailArrayList.get(i).name);
            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            params5.weight=1;
            params5.setMargins(DPS(20), 0, 0, DPS(15));
            tv2.setLayoutParams(params5);
            tv2.setTextSize(DPS(8));
            tv2.setTypeface(title2);
            tv2.setGravity(Gravity.CENTER_VERTICAL);
            tv2.setTextColor(Color.parseColor("#323031"));

            cv1.addView(iv1);
            ll.addView(cv1);
            ll.addView(tv2);
            ((LinearLayout)findViewById(R.id.collection_list_layout)).addView(ll);
        }



    }


    // DPS to Pixels Function
    private int DPS(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
