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
        final Typeface main_title = getResources().getFont(R.font.gothamblack);
        final Typeface sub_sub_title = getResources().getFont(R.font.gothammedium);



        // Get Title & Photo
        String collectionName = getIntent().getStringExtra("title");

        if(collectionName.equals("Top 10")){
            FeaturedCocktailArrayList.add(new cocktail("11000","Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11001","Old Fashioned", "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11002","Long Island Tea","https://www.thecocktaildb.com/images/media/drink/ywxwqs1439906072.jpg"));
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
            FeaturedCocktailArrayList.add(new cocktail("11000","Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11001","Old Fashioned", "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11002","Long Island Tea","https://www.thecocktaildb.com/images/media/drink/ywxwqs1439906072.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11004","Whiskey Sour","https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11005","Dry Martini","https://www.thecocktaildb.com/images/media/drink/71t8581504353095.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11006","Daiquiri","https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11007","Margarita","https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11008","Manhattan","https://www.thecocktaildb.com/images/media/drink/ec2jtz1504350429.jpg"));
            FeaturedCocktailArrayList.add(new cocktail("11009","Moscow Mule","https://www.thecocktaildb.com/images/media/drink/3pylqc1504370988.jpg"));
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
        if(collectionName.equals("Top 10")){
            iv.setImageDrawable(getResources().getDrawable(R.drawable.top));
        }else if (collectionName.equals("Christmas Collection")){
            iv.setImageDrawable(getResources().getDrawable(R.drawable.xmas));
        }else{
            iv.setImageDrawable(getResources().getDrawable(R.drawable.sad));
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

        // Featured List
        for(int i = 0; i<FeaturedCocktailArrayList.size(); i++){
            /* Linear Layout */
            final LinearLayout ll = new LinearLayout(this);
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
            new MainActivity.DownloadImageTask(iv1).execute(FeaturedCocktailArrayList.get(i).image + "/preview");

            /* TextView */
            TextView tv2 = new TextView(this);
            tv2.setText(FeaturedCocktailArrayList.get(i).name);
            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            params5.weight=1;
            params5.setMargins(DPS(20), 0, 0, DPS(15));
            tv2.setLayoutParams(params5);
            tv2.setTextSize(DPS(8));
            tv2.setTypeface(sub_sub_title);
            tv2.setGravity(Gravity.CENTER_VERTICAL);
            tv2.setTextColor(Color.parseColor("#323031"));

            cv1.addView(iv1);
            ll.addView(cv1);
            ll.addView(tv2);
            ((LinearLayout)findViewById(R.id.collection_list_layout)).addView(ll);

            final int finalI = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.addRecent(FeaturedCocktailArrayList.get(finalI).id,FeaturedCocktailArrayList.get(finalI).name,FeaturedCocktailArrayList.get(finalI).image);
                    Intent intent = new Intent(ll.getContext(), DetailsView.class);
                    intent.putExtra("id",FeaturedCocktailArrayList.get(finalI).id );
                    ll.getContext().startActivity(intent);
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
