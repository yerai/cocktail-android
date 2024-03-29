package com.example.cocktail;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private static ArrayList<cocktail> RecentCocktailArrayList = new ArrayList<cocktail>();
    private static ArrayList<cocktail> FavoritesCocktailArrayList = new ArrayList<cocktail>();

    final MainActivity context = this;

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

        setContentView(R.layout.activity_main);

        /*
        addFavorite("11007","Margarita","https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg");
        addFavorite("12784","Thai Iced Coffee","https://www.thecocktaildb.com/images/media/drink/rqpypv1441245650.jpg");
        addRecent("11006","Daiquiri","https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg");
        */
        //Fonts
        final Typeface main_title = getResources().getFont(R.font.gothamblack);
        final Typeface sub_sub_title = getResources().getFont(R.font.gothammedium);
        final Typeface text = getResources().getFont(R.font.montserratrregular);

        // Set titles
        ((TextView)findViewById(R.id.Title)).setTypeface(main_title);
        ((TextView)findViewById(R.id.Title)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Recent)).setTypeface(main_title);
        ((TextView)findViewById(R.id.Recent)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Featured)).setTypeface(main_title);
        ((TextView)findViewById(R.id.Featured)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Categories)).setTypeface(main_title);
        ((TextView)findViewById(R.id.Categories)).setTextColor(Color.parseColor("#343035"));

        // Navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_favorites:
                        Intent b = new Intent(MainActivity.this,Favorites.class);
                        startActivity(b);

                        break;
                }
                return false;
            }
        });

        // Set Searchbar
        final SearchView sv = findViewById(R.id.searchBar);
        sv.setBackgroundResource(R.drawable.searchview_rounded);

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.onActionViewExpanded();

            }
        });


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final MainActivity context = this;

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    callSearch(newText);
                }else{
                    ((LinearLayout)findViewById(R.id.Search_Results)).removeAllViews();
                }
                return false;
            }

            public void callSearch(String query) {

                String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Iterator<?> keys = response.keys();
                                while(keys.hasNext() ) {
                                    String key = (String)keys.next();
                                    try {
                                        JSONArray drinks = new JSONArray(response.get(key).toString());
                                        ((LinearLayout)findViewById(R.id.Search_Results)).removeAllViews();

                                        for(int i=0; i<drinks.length() && i<5; i++){

                                            final JSONObject drink = drinks.getJSONObject(i);

                                            final LinearLayout ll = new LinearLayout(context);
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            ll.setLayoutParams(params);
                                            ll.setOrientation(LinearLayout.HORIZONTAL);

                                            /* CardView */
                                            CardView cv1 = new CardView(context);
                                            cv1.setCardElevation(DPS(0));
                                            cv1.setRadius(DPS(5));
                                            CardView.LayoutParams params3 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(70),DPS(70)));
                                            params3.setMargins(DPS(0),DPS(0),DPS(0),DPS(15));
                                            cv1.setLayoutParams(params3);

                                            /* ImageView */
                                            ImageView iv1 = new ImageView(context);
                                            iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                            iv1.setLayoutParams(params4);
                                            new MainActivity.DownloadImageTask(iv1).execute(drink.getString("strDrinkThumb")+"/preview");

                                            /* TextView */
                                            TextView tv2 = new TextView(context);
                                            tv2.setText(drink.getString("strDrink"));
                                            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                            params5.weight=1;
                                            params5.setMargins(DPS(20), 0, 0, DPS(15));
                                            tv2.setLayoutParams(params5);
                                            tv2.setTextSize(DPS(6));
                                            tv2.setTypeface(sub_sub_title);
                                            tv2.setGravity(Gravity.CENTER_VERTICAL);
                                            tv2.setTextColor(Color.parseColor("#323031"));

                                            cv1.addView(iv1);
                                            ll.addView(cv1);
                                            ll.addView(tv2);
                                            ((LinearLayout)findViewById(R.id.Search_Results)).addView(ll);

                                            ll.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    try {
                                                        RecentCocktailArrayList.add(0,new cocktail(drink.getString("idDrink"),drink.getString("strDrink"), drink.getString("strDrinkThumb")));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Intent intent = new Intent(ll.getContext(), DetailsView.class);
                                                    try {
                                                        intent.putExtra("id", drink.getString("idDrink"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    ll.getContext().startActivity(intent);
                                                }
                                            });

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("hello", error.toString());
                            }
                        });

                // add it to the RequestQueue
                requestQueue.add(jsonObjectRequest);

            }
        });


        // Set Recent Cocktails
        if(RecentCocktailArrayList == null || RecentCocktailArrayList.size()<1){
            findViewById(R.id.Recent).setVisibility(View.GONE);
            findViewById(R.id.Recent_List).setVisibility(View.GONE);
        }else{

            findViewById(R.id.Recent).setVisibility(View.VISIBLE);
            findViewById(R.id.Recent_List).setVisibility(View.VISIBLE);

            ((LinearLayout)findViewById(R.id.Recent_List_Container)).removeAllViews();

            for (int i=0; i<RecentCocktailArrayList.size(); i++){

                /* Linear Layout */
                final LinearLayout ll = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setLayoutParams(params);
                ll.setOrientation(LinearLayout.VERTICAL);

                /* CardView */
                CardView cv = new CardView(this);
                cv.setCardElevation(DPS(0));
                cv.setRadius(DPS(5));
                CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(130),DPS(130)));
                if(i==0){
                    params1.setMargins(DPS(20),DPS(0),DPS(10),DPS(0));
                }else if(i== RecentCocktailArrayList.size()-1){
                    params1.setMargins(DPS(5),DPS(0),DPS(20),DPS(0));
                }else{
                    params1.setMargins(DPS(5),DPS(0),DPS(10),DPS(0));
                }
                cv.setLayoutParams(params1);

                /* ImageView */
                ImageView iv = new ImageView(this);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                params2.weight=1;
                iv.setLayoutParams(params2);
                new DownloadImageTask(iv).execute(RecentCocktailArrayList.get(i).image);

                /* TextView */
                TextView tv = new TextView(this);
                tv.setText(RecentCocktailArrayList.get(i).name);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params3.weight=1;
                if(i==0){
                    params3.setMargins(DPS(20), 0, 0, 0);
                }else {
                    params3.setMargins(DPS(5), 0, 0, 0);
                }
                tv.setLayoutParams(params3);
                tv.setTextSize(DPS(7));
                Typeface sub_title = getResources().getFont(R.font.gothammedium);
                tv.setTypeface(sub_title);
                tv.setTextColor(Color.parseColor("#323031"));

                /* Add Components */
                cv.addView(iv);
                ll.addView(cv);
                ll.addView(tv);
                ((LinearLayout)findViewById(R.id.Recent_List_Container)).addView(ll);

                final int finalI = i;
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ll.getContext(), DetailsView.class);
                        intent.putExtra("id", RecentCocktailArrayList.get(finalI).id);
                        ll.getContext().startActivity(intent);
                    }
                });
            }

        }

        // Featured Cocktails
        for (int i=0; i<2; i++){

            final String name;
            String description;
            if (i==0){
               name = "Top 10";
                description = "Our top selection of cocktails!";
            }else{
                name ="Christmas Collection";
                description = "Best selection to greet Santa.";
            }

            /* Linear Layout */
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ll.setLayoutParams(params);
            ll.setOrientation(LinearLayout.VERTICAL);

            /* CardView */
            final CardView cv = new CardView(this);
            cv.setCardElevation(DPS(0));
            cv.setRadius(DPS(5));
            CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(300),DPS(130)));
            if(i==0){
                params1.setMargins(DPS(20),DPS(0),DPS(10),DPS(0));
            }else if(i==1){
                params1.setMargins(DPS(5),DPS(0),DPS(20),DPS(0));
            }else{
                params1.setMargins(DPS(5),DPS(0),DPS(10),DPS(0));
            }
            cv.setLayoutParams(params1);

            /* ImageView */
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            params2.weight=1;
            iv.setLayoutParams(params2);
            if(i==0){
                iv.setImageDrawable(getResources().getDrawable(R.drawable.top));
            }else {
                iv.setImageDrawable(getResources().getDrawable(R.drawable.xmas));
            }

            /* TextView */
            TextView tv = new TextView(this);
            tv.setText(name);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params3.weight=1;
            if(i==0){
                params3.setMargins(DPS(20), 0, 0, 0);
            }else {
                params3.setMargins(DPS(5), 0, 0, 0);
            }
            tv.setLayoutParams(params3);
            tv.setTextSize(DPS(7));
            tv.setTypeface(sub_sub_title);
            tv.setTextColor(Color.parseColor("#323031"));

            /* TextView */
            TextView tv2 = new TextView(this);
            tv2.setText(description);
            tv2.setLayoutParams(params3);
            tv2.setTextSize(DPS(5));
            tv2.setTypeface(text);
            tv2.setTextColor(Color.parseColor("#323031"));

            /* On click*/
            final int finali = i;
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(cv.getContext(), collection.class);
                    intent.putExtra("title", name);
                    cv.getContext().startActivity(intent);
                }
            });

            /* Add Components */
            cv.addView(iv);
            ll.addView(cv);
            ll.addView(tv);
            ll.addView(tv2);
            ((LinearLayout)findViewById(R.id.Featured_List_Container)).addView(ll);

        }

        // Mood
        /* CardView */
        final CardView cv = new CardView(this);
        cv.setCardElevation(DPS(0));
        cv.setRadius(DPS(5));
        CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(176),DPS(84)));
        params1.setMargins(DPS(20),DPS(0),DPS(15),DPS(15));
        cv.setLayoutParams(params1);
        /* ImageView */
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        params2.weight=1;
        iv.setLayoutParams(params2);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.mood));
        /* Black Background */
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(params2);
        tv2.setBackgroundColor(Color.parseColor("#80343035"));
        tv2.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv = new TextView(this);
        tv.setText("Mood");
        tv.setLayoutParams(params2);
        tv.setTextSize(DPS(9));
        tv.setTypeface(main_title);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setGravity(Gravity.CENTER);
        /* Add components */
        cv.addView(iv);
        cv.addView(tv2);
        cv.addView(tv);
        ((LinearLayout)findViewById(R.id.Categories1)).addView(cv);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cv.getContext(), categorie.class);
                intent.putExtra("title", "Moods");
                cv.getContext().startActivity(intent);
            }
        });


        // Alcohol
        /* CardView */
        CardView cv2 = new CardView(this);
        cv2.setCardElevation(DPS(0));
        cv2.setRadius(DPS(5));
        CardView.LayoutParams params3 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(176),DPS(84)));
        params3.setMargins(DPS(0),DPS(0),DPS(20),DPS(15));
        cv2.setLayoutParams(params3);
        /* ImageView */
        ImageView iv2 = new ImageView(this);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv2.setLayoutParams(params2);
        iv2.setImageDrawable(getResources().getDrawable(R.drawable.alcohol));
        //new DownloadImageTask(iv2).execute("http://www.todayifoundout.com/wp-content/uploads/2017/05/alcohol-768x513.png");
        /* Black Background */
        TextView tv4 = new TextView(this);
        tv4.setLayoutParams(params2);
        tv4.setBackgroundColor(Color.parseColor("#80343035"));
        tv4.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv3 = new TextView(this);
        tv3.setText("Alcohol");
        tv3.setLayoutParams(params2);
        tv3.setTextSize(DPS(9));
        tv3.setTypeface(main_title);
        tv3.setTextColor(Color.parseColor("#ffffff"));
        tv3.setGravity(Gravity.CENTER);
        /* Add components */
        cv2.addView(iv2);
        cv2.addView(tv4);
        cv2.addView(tv3);
        ((LinearLayout)findViewById(R.id.Categories1)).addView(cv2);


        // Ingredients
        /* CardView */
        final CardView cv3 = new CardView(this);
        cv3.setCardElevation(DPS(0));
        cv3.setRadius(DPS(5));
        cv3.setLayoutParams(params1);
        /* ImageView */
        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv3.setLayoutParams(params2);
        iv3.setImageDrawable(getResources().getDrawable(R.drawable.fruit));
        //new DownloadImageTask(iv3).execute("https://cdn-prod.medicalnewstoday.com/content/images/articles/308/308796/mixed-fruits.jpg");
        /* Black Background */
        TextView tv6 = new TextView(this);
        tv6.setLayoutParams(params2);
        tv6.setBackgroundColor(Color.parseColor("#80343035"));
        tv6.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv5 = new TextView(this);
        tv5.setText("Ingredients");
        tv5.setLayoutParams(params2);
        tv5.setTextSize(DPS(9));
        tv5.setTypeface(main_title);
        tv5.setTextColor(Color.parseColor("#ffffff"));
        tv5.setGravity(Gravity.CENTER);
        /* Add components */
        cv3.addView(iv3);
        cv3.addView(tv6);
        cv3.addView(tv5);
        ((LinearLayout)findViewById(R.id.Categories2)).addView(cv3);

        // Country
        /* CardView */
        final CardView cv4 = new CardView(this);
        cv4.setCardElevation(DPS(0));
        cv4.setRadius(DPS(5));
        cv4.setLayoutParams(params3);
        /* ImageView */
        ImageView iv4 = new ImageView(this);
        iv4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv4.setLayoutParams(params2);
        iv4.setImageDrawable(getResources().getDrawable(R.drawable.countries));
        //new DownloadImageTask(iv4).execute("https://image.freepik.com/free-vector/tourists-landmarks-poster_1284-11165.jpg");
        /* Black Background */
        TextView tv8 = new TextView(this);
        tv8.setLayoutParams(params2);
        tv8.setBackgroundColor(Color.parseColor("#80343035"));
        tv8.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv7 = new TextView(this);
        tv7.setText("Country");
        tv7.setLayoutParams(params2);
        tv7.setTextSize(DPS(9));
        tv7.setTypeface(main_title);
        tv7.setTextColor(Color.parseColor("#ffffff"));
        tv7.setGravity(Gravity.CENTER);
        /* Add components */
        cv4.addView(iv4);
        cv4.addView(tv8);
        cv4.addView(tv7);
        ((LinearLayout)findViewById(R.id.Categories2)).addView(cv4);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        // Set Recent Cocktails
        if(RecentCocktailArrayList == null || RecentCocktailArrayList.size()<1){
            findViewById(R.id.Recent).setVisibility(View.GONE);
            findViewById(R.id.Recent_List).setVisibility(View.GONE);
        }else{

            findViewById(R.id.Recent).setVisibility(View.VISIBLE);
            findViewById(R.id.Recent_List).setVisibility(View.VISIBLE);

            ((LinearLayout)findViewById(R.id.Recent_List_Container)).removeAllViews();

            for (int i=0; i<RecentCocktailArrayList.size(); i++){

                /* Linear Layout */
                final LinearLayout ll = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.setLayoutParams(params);
                ll.setOrientation(LinearLayout.VERTICAL);

                /* CardView */
                CardView cv = new CardView(this);
                cv.setCardElevation(DPS(0));
                cv.setRadius(DPS(5));
                CardView.LayoutParams params1 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(130),DPS(130)));
                if(i==0){
                    params1.setMargins(DPS(20),DPS(0),DPS(10),DPS(0));
                }else if(i== RecentCocktailArrayList.size()-1){
                    params1.setMargins(DPS(5),DPS(0),DPS(20),DPS(0));
                }else{
                    params1.setMargins(DPS(5),DPS(0),DPS(10),DPS(0));
                }
                cv.setLayoutParams(params1);

                /* ImageView */
                ImageView iv = new ImageView(this);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                params2.weight=1;
                iv.setLayoutParams(params2);
                new DownloadImageTask(iv).execute(RecentCocktailArrayList.get(i).image);

                /* TextView */
                TextView tv = new TextView(this);
                tv.setText(RecentCocktailArrayList.get(i).name);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params3.weight=1;
                if(i==0){
                    params3.setMargins(DPS(20), 0, 0, 0);
                }else {
                    params3.setMargins(DPS(5), 0, 0, 0);
                }
                tv.setLayoutParams(params3);
                tv.setTextSize(DPS(7));
                Typeface sub_title = getResources().getFont(R.font.gothammedium);
                tv.setTypeface(sub_title);
                tv.setTextColor(Color.parseColor("#323031"));

                /* Add Components */
                cv.addView(iv);
                ll.addView(cv);
                ll.addView(tv);
                ((LinearLayout)findViewById(R.id.Recent_List_Container)).addView(ll);

                final int finalI = i;
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ll.getContext(), DetailsView.class);
                        intent.putExtra("id", RecentCocktailArrayList.get(finalI).id);
                        ll.getContext().startActivity(intent);
                    }
                });
            }

        }
    }


    static void addRecent(String id, String name, String image){
        for(int i=0; i<RecentCocktailArrayList.size(); i++){
            if (RecentCocktailArrayList.get(i).id.equals(id)){
               RecentCocktailArrayList.remove(i);
            }
        }
        RecentCocktailArrayList.add(0,new cocktail(id,name,image));
    }

    static void addFavorite(String id, String name, String image){
        FavoritesCocktailArrayList.add(0,new cocktail(id,name,image));
    }

    static void removeFavorite(int id){
        FavoritesCocktailArrayList.remove(id);
    }

    static void removeFavoriteByID(String id){
        for (int i = 0; i<FavoritesCocktailArrayList.size(); i++){
            if (FavoritesCocktailArrayList.get(i).id.equals(id)){
                FavoritesCocktailArrayList.remove(id);
            }
        }
    }

    static boolean checkFavorite (String id){
        boolean result = false;
        for (int i = 0; i<FavoritesCocktailArrayList.size(); i++){
            if (FavoritesCocktailArrayList.get(i).id.equals(id)){
               result = true;
            }
        }
        return result;
    }

    static ArrayList<cocktail> getFavorites(){
        return FavoritesCocktailArrayList;
    }

    // DPS to Pixels Function
    private int DPS(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    // Function to download IMG from URL
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
