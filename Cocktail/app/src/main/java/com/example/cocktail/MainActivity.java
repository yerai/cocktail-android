package com.example.cocktail;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private ArrayList<cocktail> RecentCocktailArrayList = new ArrayList<cocktail>();
    private ArrayList<featured> FeaturedArrayList = new ArrayList<featured>();

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

        //Fonts
        Typeface title = getResources().getFont(R.font.fredoka);
        final Typeface title2 = getResources().getFont(R.font.latobold);
        Typeface title3 = getResources().getFont(R.font.latoblack);
        Typeface text = getResources().getFont(R.font.montserratrregular);

        // Add Featured Cocktails
        FeaturedArrayList.add(new featured("Top 15", "https://github.com/yerai/cocktail/blob/master/img/featured-1.jpg?raw=true", "Our top selection of Cocktails!"));
        FeaturedArrayList.add(new featured("Christmas Collection", "https://github.com/yerai/cocktail/blob/master/img/featured-2.jpg?raw=true", "Best selection to greet Santa."));

        // Add Recent Cocktails
        RecentCocktailArrayList.add(new cocktail("Bloody Mary", "https://www.thecocktaildb.com/images/media/drink/uyquuu1439906954.jpg"));
        RecentCocktailArrayList.add(new cocktail("Mojito", "https://www.thecocktaildb.com/images/media/drink/rxtqps1478251029.jpg"));
        RecentCocktailArrayList.add(new cocktail("Daiquiri", "https://www.thecocktaildb.com/images/media/drink/usuuur1439906797.jpg"));
        RecentCocktailArrayList.add(new cocktail("Whiskey Sour", "https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));

        // Set titles
        ((TextView)findViewById(R.id.Title)).setTypeface(title);
        ((TextView)findViewById(R.id.Title)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Recent)).setTypeface(title);
        ((TextView)findViewById(R.id.Recent)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Featured)).setTypeface(title);
        ((TextView)findViewById(R.id.Featured)).setTextColor(Color.parseColor("#343035"));
        ((TextView)findViewById(R.id.Categories)).setTypeface(title);
        ((TextView)findViewById(R.id.Categories)).setTextColor(Color.parseColor("#343035"));

        // Set Searchbar
        final SearchView sv = findViewById(R.id.searchBar);
        sv.setBackgroundResource(R.drawable.searchview_rounded);

        /*LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        params8.setMargins(DPS(20),DPS(10),DPS(20),DPS(10));
        params8.weight= (float) 0.5;
        sv.setLayoutParams(params8);

        TextView cs = findViewById(R.id.closeSearch);
        cs.setVisibility(View.GONE);

        cs.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                findViewById(R.id.Recent).setVisibility(View.VISIBLE);
                findViewById(R.id.Recent_List).setVisibility(View.VISIBLE);
                findViewById(R.id.Featured).setVisibility(View.VISIBLE);
                findViewById(R.id.Featured_List).setVisibility(View.VISIBLE);
                findViewById(R.id.Categories).setVisibility(View.VISIBLE);
                findViewById(R.id.Categories1).setVisibility(View.VISIBLE);
                findViewById(R.id.Categories2).setVisibility(View.VISIBLE);
                findViewById(R.id.Categories3).setVisibility(View.VISIBLE);
                findViewById(R.id.closeSearch).setVisibility(View.GONE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(DPS(20),DPS(10),DPS(20),DPS(10));
                params.weight= (float) 0.5;
                sv.setLayoutParams(params);
                sv.clearFocus();

            }
        });

        */
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hello", "click");
                sv.onActionViewExpanded();
                /*findViewById(R.id.Recent).setVisibility(View.GONE);
                findViewById(R.id.Recent_List).setVisibility(View.GONE);
                findViewById(R.id.Featured).setVisibility(View.GONE);
                findViewById(R.id.Featured_List).setVisibility(View.GONE);
                findViewById(R.id.Categories).setVisibility(View.GONE);
                findViewById(R.id.Categories1).setVisibility(View.GONE);
                findViewById(R.id.Categories2).setVisibility(View.GONE);
                findViewById(R.id.Categories3).setVisibility(View.GONE);
                findViewById(R.id.closeSearch).setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(DPS(20),DPS(10),DPS(0),DPS(10));
                params.weight= (float) 0.5;
                sv.setLayoutParams(params);*/

            }
        });


        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final MainActivity context = this;

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("hello", "one");
                callSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    Log.d("hello", "two");
                    callSearch(newText);
                }else{
                    Log.d("hello", "three");
                    ((LinearLayout)findViewById(R.id.Search_Results)).removeAllViews();
                    Log.d("hello", "four");
                }
                return false;
            }

            public void callSearch(String query) {

                String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + query;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hello", "five");
                                Iterator<?> keys = response.keys();
                                while(keys.hasNext() ) {
                                    String key = (String)keys.next();
                                    try {
                                        JSONArray drinks = new JSONArray(response.get(key).toString());
                                        ((LinearLayout)findViewById(R.id.Search_Results)).removeAllViews();

                                        for(int i=0; i<drinks.length(); i++){
                                            final JSONObject drink = drinks.getJSONObject(i);

                                            final LinearLayout ll = new LinearLayout(context);
                                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                            ll.setLayoutParams(params);
                                            ll.setOrientation(LinearLayout.HORIZONTAL);

                                            /* CardView */
                                            CardView cv1 = new CardView(context);
                                            cv1.setCardElevation(DPS(0));
                                            cv1.setRadius(DPS(5));
                                            CardView.LayoutParams params3 = new CardView.LayoutParams(new LinearLayout.LayoutParams(DPS(90),DPS(90)));
                                            params3.setMargins(DPS(0),DPS(0),DPS(0),DPS(15));
                                            cv1.setLayoutParams(params3);

                                            /* ImageView */
                                            ImageView iv1 = new ImageView(context);
                                            iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                            LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                            iv1.setLayoutParams(params4);
                                            new MainActivity.DownloadImageTask(iv1).execute(drink.getString("strDrinkThumb"));

                                            /* TextView */
                                            TextView tv2 = new TextView(context);
                                            tv2.setText(drink.getString("strDrink"));
                                            LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                            params5.weight=1;
                                            params5.setMargins(DPS(20), 0, 0, DPS(15));
                                            tv2.setLayoutParams(params5);
                                            tv2.setTextSize(DPS(6));
                                            tv2.setTypeface(title2);
                                            tv2.setGravity(Gravity.CENTER_VERTICAL);
                                            tv2.setTextColor(Color.parseColor("#323031"));

                                            cv1.addView(iv1);
                                            ll.addView(cv1);
                                            ll.addView(tv2);
                                            ((LinearLayout)findViewById(R.id.Search_Results)).addView(ll);

                                            ll.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    RecentCocktailArrayList.add(new cocktail("hol", "https://www.thecocktaildb.com/images/media/drink/o56h041504352725.jpg"));
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

            for (int i=0; i<RecentCocktailArrayList.size(); i++){

                /* Linear Layout */
                LinearLayout ll = new LinearLayout(this);
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
                tv.setTextSize(DPS(5));
                tv.setTypeface(title2);
                tv.setTextColor(Color.parseColor("#323031"));

                /* Add Components */
                cv.addView(iv);
                ll.addView(cv);
                ll.addView(tv);
                ((LinearLayout)findViewById(R.id.Recent_List_Container)).addView(ll);
            }



        }

        // Featured Cocktails
        for (int i=0; i<FeaturedArrayList.size(); i++){

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
            }else if(i== FeaturedArrayList.size()-1){
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
            new DownloadImageTask(iv).execute(FeaturedArrayList.get(i).image);

            /* TextView */
            TextView tv = new TextView(this);
            tv.setText(FeaturedArrayList.get(i).name);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params3.weight=1;
            if(i==0){
                params3.setMargins(DPS(20), 0, 0, 0);
            }else {
                params3.setMargins(DPS(5), 0, 0, 0);
            }
            tv.setLayoutParams(params3);
            tv.setTextSize(DPS(5));
            tv.setTypeface(title2);
            tv.setTextColor(Color.parseColor("#323031"));

            /* TextView */
            TextView tv2 = new TextView(this);
            tv2.setText(FeaturedArrayList.get(i).description);
            tv2.setLayoutParams(params3);
            tv2.setTextSize(DPS(4));
            tv2.setTypeface(text);
            tv2.setTextColor(Color.parseColor("#323031"));

            /* On click*/
            final int finali = i;
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(cv.getContext(), collection.class);
                    intent.putExtra("title", FeaturedArrayList.get(finali).name);
                    intent.putExtra("photo", FeaturedArrayList.get(finali).image);
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
        new DownloadImageTask(iv).execute("https://cdn.dribbble.com/users/216803/screenshots/1465689/moods.png");
        /* Black Background */
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(params2);
        tv2.setBackgroundColor(Color.parseColor("#80343035"));
        tv2.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv = new TextView(this);
        tv.setText("Mood");
        tv.setLayoutParams(params2);
        tv.setTextSize(DPS(7));
        tv.setTypeface(title3);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setGravity(Gravity.CENTER);
        /* Add components */
        cv.addView(iv);
        cv.addView(tv2);
        cv.addView(tv);
        ((LinearLayout)findViewById(R.id.Categories1)).addView(cv);


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
        new DownloadImageTask(iv2).execute("http://www.todayifoundout.com/wp-content/uploads/2017/05/alcohol-768x513.png");
        /* Black Background */
        TextView tv4 = new TextView(this);
        tv4.setLayoutParams(params2);
        tv4.setBackgroundColor(Color.parseColor("#80343035"));
        tv4.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv3 = new TextView(this);
        tv3.setText("Alcohol");
        tv3.setLayoutParams(params2);
        tv3.setTextSize(DPS(7));
        tv3.setTypeface(title3);
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
        new DownloadImageTask(iv3).execute("https://cdn-prod.medicalnewstoday.com/content/images/articles/308/308796/mixed-fruits.jpg");
        /* Black Background */
        TextView tv6 = new TextView(this);
        tv6.setLayoutParams(params2);
        tv6.setBackgroundColor(Color.parseColor("#80343035"));
        tv6.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv5 = new TextView(this);
        tv5.setText("Ingredients");
        tv5.setLayoutParams(params2);
        tv5.setTextSize(DPS(7));
        tv5.setTypeface(title3);
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
        new DownloadImageTask(iv4).execute("https://image.freepik.com/free-vector/tourists-landmarks-poster_1284-11165.jpg");
        /* Black Background */
        TextView tv8 = new TextView(this);
        tv8.setLayoutParams(params2);
        tv8.setBackgroundColor(Color.parseColor("#80343035"));
        tv8.setGravity(Gravity.CENTER);
        /* Text View */
        TextView tv7 = new TextView(this);
        tv7.setText("Country");
        tv7.setLayoutParams(params2);
        tv7.setTextSize(DPS(7));
        tv7.setTypeface(title3);
        tv7.setTextColor(Color.parseColor("#ffffff"));
        tv7.setGravity(Gravity.CENTER);
        /* Add components */
        cv4.addView(iv4);
        cv4.addView(tv8);
        cv4.addView(tv7);
        ((LinearLayout)findViewById(R.id.Categories2)).addView(cv4);


        /* Go to Favorites */
        /* On click*/


        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cv3.getContext(), Favorites.class);
                cv3.getContext().startActivity(intent);
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cv4.getContext(), DetailsView.class);
                cv4.getContext().startActivity(intent);
            }
        });

    }


    // DPS to Pixels Function
    private int DPS(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    // Function to download IMG from URL
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


        /*new DownloadImageTask((ImageView) findViewById(R.id.img_recent1))
                    .execute(cocktailArrayList.get(0).image);
            ((TextView)findViewById(R.id.text_recent1)).setText(cocktailArrayList.get(0).name);*/

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
