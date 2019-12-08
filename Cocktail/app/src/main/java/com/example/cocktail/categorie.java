package com.example.cocktail;

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

import java.util.ArrayList;

public class categorie extends AppCompatActivity {

    private ArrayList<cocktail> FeaturedCocktailArrayList = new ArrayList<cocktail>();

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        //Fonts
        final Typeface main_title = getResources().getFont(R.font.gothamblack);
        final Typeface sub_sub_title = getResources().getFont(R.font.gothammedium);
        final Typeface text = getResources().getFont(R.font.montserratrregular);


        // Get Title & Photo
        String collectionName = getIntent().getStringExtra("title");

        if(collectionName.equals("Top 10")){
            FeaturedCocktailArrayList.add(new cocktail("11000","Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11001","Old Fashioned", "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11002","Long Island Tea","https://www.thecocktaildb.com/images/media/drink/ywxwqs1439906072.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11003","Negroni","https://www.thecocktaildb.com/images/media/drink/qgdu971561574065.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11004","Whiskey Sour","https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11005","Dry Martini","https://www.thecocktaildb.com/images/media/drink/71t8581504353095.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11006","Daiquiri","https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11007","Margarita","https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11008","Manhattan","https://www.thecocktaildb.com/images/media/drink/ec2jtz1504350429.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11009","Moscow Mule","https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg"));
        }else if(collectionName.equals("Christmas Collection")){
            FeaturedCocktailArrayList.add(new cocktail("12784","Thai Iced Coffee","https://www.thecocktaildb.com/images/media/drink/rqpypv1441245650.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("17167","Raspberry Cooler","https://www.thecocktaildb.com/images/media/drink/suqyyx1441254346.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("14842","Midnight Mint","https://www.thecocktaildb.com/images/media/drink/svuvrq1441208310.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("12770","Iced Coffee","https://www.thecocktaildb.com/images/media/drink/ytprxy1454513855.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("17220","Butter Baby","https://www.thecocktaildb.com/images/media/drink/1xhjk91504783763.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("15254","Zenmeister","https://www.thecocktaildb.com/images/media/drink/qyuvsu1479209462.jpg"));
        }else{

        }

        /* CardView */
        final CardView cv = new CardView(this);
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
        if(collectionName.equals("Moods")){
            iv.setImageDrawable(getResources().getDrawable(R.drawable.mood));
        }
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
        tv1.setTypeface(main_title);
        tv1.setTextColor(Color.parseColor("#ffffff"));
        tv1.setGravity(Gravity.CENTER);
        /* Add components */
        cv.addView(iv);
        cv.addView(tv);
        cv.addView(tv1);
        ((LinearLayout)findViewById(R.id.collection_layout)).addView(cv);


        final String[] title = {
                "Happy",
                "Sick",
                "In Love",
                "Sad",
                "Angry",
                "Sleepy"
        };


        for(int i= 0; i<6; i++){

            /* CardView */
            final CardView cv1 = new CardView(this);
            cv1.setCardElevation(DPS(0));
            cv1.setRadius(DPS(5));
            CardView.LayoutParams params3 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(176),DPS(84)));
            params3.setMargins(DPS(15),DPS(15),DPS(15),DPS(0));
            //params3.setMargins(0,0,0,0);
            cv1.setLayoutParams(params3);

            /* ImageView */
            ImageView iv2 = new ImageView(this);
            iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            params4.weight=1;
            iv2.setLayoutParams(params4);
            if(i==0){
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.happy));
            }else if(i==1){
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.sik));
            }else if(i==2){
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.love));
            } else if(i==3){
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.sad));
            } else if(i==4){
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.angry));
            }else{
                iv2.setImageDrawable(getResources().getDrawable(R.drawable.sleep));
            }


            /* Black Background */
            TextView tv2 = new TextView(this);
            tv2.setLayoutParams(params4);
            tv2.setBackgroundColor(Color.parseColor("#80343035"));
            tv2.setGravity(Gravity.CENTER);

            /* Text View */
            TextView tv4 = new TextView(this);
            tv4.setText(title[i]);
            tv4.setLayoutParams(params4);
            tv4.setTextSize(DPS(9));
            tv4.setTypeface(main_title);
            tv4.setTextColor(Color.parseColor("#ffffff"));
            tv4.setGravity(Gravity.CENTER);

            /* Add components */
            cv1.addView(iv2);
            cv1.addView(tv2);
            cv1.addView(tv4);

            if(i%2==0){
                ((LinearLayout) findViewById(R.id.first_column)).addView(cv1);
            }else{
                ((LinearLayout) findViewById(R.id.second_column)).addView(cv1);
            }

            final int finalI = i;
            cv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(cv1.getContext(), collection.class);
                    intent.putExtra("title", title[finalI]);
                    cv1.getContext().startActivity(intent);
                }
            });

        }

    }

    // DPS to Pixels Function
    private int DPS(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}


