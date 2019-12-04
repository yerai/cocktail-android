package com.example.cocktail;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Iterator;

public class DetailsView extends AppCompatActivity {

    public static final String TAG = DetailsView.class.getSimpleName();

    TextView textView;
    ToggleButton toggleButton;
    Spinner spinner;
    Button button;

    ArrayList<HashMap<String, String>> ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Top Bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        // Get ID from View
        String id = getIntent().getStringExtra("id");
        // Get Data of Cocktail
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id;

        setContentView(R.layout.activity_details_view);

        spinner = findViewById(R.id.servings_spinner);
        toggleButton = findViewById(R.id.myToggleButton);
        button = findViewById(R.id.btn_share);
        ingredientsList = new ArrayList<>();

        // Data structure of cocktail
        final cocktail cocktail_info = new cocktail();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        Iterator<?> keys = response.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            try {
                                JSONArray drinks = new JSONArray(response.get(key).toString());

                                for (int i = 0; i < drinks.length(); i++) {
                                    final JSONObject drink = drinks.getJSONObject(i);

                                    cocktail_info.name = drink.getString("strDrink");
                                    cocktail_info.image = drink.getString("strDrinkThumb");
                                    cocktail_info.glass = drink.getString("strGlass");
                                    cocktail_info.instructions = drink.getString("strInstructions");
                                    cocktail_info.ingredient1 = drink.getString("strIngredient1");
                                    cocktail_info.ingredient2 = drink.getString("strIngredient2");
                                    cocktail_info.ingredient3 = drink.getString("strIngredient3");
                                    cocktail_info.ingredient4 = drink.getString("strIngredient4");
                                    cocktail_info.ingredient5 = drink.getString("strIngredient5");
                                    cocktail_info.ingredient6 = drink.getString("strIngredient6");
                                    cocktail_info.ingredient7 = drink.getString("strIngredient7");
                                    cocktail_info.ingredient8 = drink.getString("strIngredient8");
                                    cocktail_info.measure1 = drink.getString("strMeasure1");
                                    cocktail_info.measure2 = drink.getString("strMeasure2");
                                    cocktail_info.measure3 = drink.getString("strMeasure3");
                                    cocktail_info.measure4 = drink.getString("strMeasure4");
                                    cocktail_info.measure5 = drink.getString("strMeasure5");
                                    cocktail_info.measure6 = drink.getString("strMeasure6");
                                    cocktail_info.measure7 = drink.getString("strMeasure7");
                                    cocktail_info.measure8 = drink.getString("strMeasure8");

                                    // Set View
                                    // Title
                                    textView = findViewById(R.id.txt_header);
                                    textView.setText(cocktail_info.name);

                                    // Image
                                    ImageView iv = findViewById(R.id.img_cocktail_details);
                                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, iv.getWidth()));
                                    iv.setLayoutParams(params4);
                                    new DetailsView.DownloadImageTask(iv).execute(cocktail_info.image);

                                    // Glass
                                    textView = findViewById(R.id.txt_glass);
                                    textView.setText(cocktail_info.glass);

                                    // Instructions
                                    textView = findViewById(R.id.txt_instructions);
                                    textView.setText(cocktail_info.instructions);

                                    //Ingredients
//                                    ImageView img = findViewById(R.id.img);

                                    //URL images ingredients
                                    String urlImgIngredient1 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient1 + "-Small.png";
                                    String urlImgIngredient2 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient2 + "-Small.png";
                                    String urlImgIngredient3 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient3 + "-Small.png";
                                    String urlImgIngredient4 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient4 + "-Small.png";
                                    String urlImgIngredient5 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient5 + "-Small.png";
                                    String urlImgIngredient6 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient6 + "-Small.png";
                                    String urlImgIngredient7 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient7 + "-Small.png";
                                    String urlImgIngredient8 = "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient8 + "-Small.png";

//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient1);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient2);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient3);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient4);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient5);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient6);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient7);
//                                        new DetailsView.DownloadImageTask(img).execute(urlImgIngredient8);

                                    Integer[] icons ={
                                            R.drawable.lemon_juice,
                                            R.drawable.lime,
                                            R.drawable.vodka,
                                            R.drawable.worcestershire_sauce,
                                            R.drawable.tomato_juice,
                                            R.drawable.tabasco_sauce,
                                            R.drawable.tomato_juice,
                                            R.drawable.tabasco_sauce
                                    };

                                    String[] ingredients ={
                                            cocktail_info.ingredient1,
                                            cocktail_info.ingredient2,
                                            cocktail_info.ingredient3,
                                            cocktail_info.ingredient4,
                                            cocktail_info.ingredient5,
                                            cocktail_info.ingredient6,
                                            cocktail_info.ingredient7,
                                            cocktail_info.ingredient8,
                                    };

                                    String[] measurements ={
                                            cocktail_info.measure1,
                                            cocktail_info.measure2,
                                            cocktail_info.measure3,
                                            cocktail_info.measure4,
                                            cocktail_info.measure5,
                                            cocktail_info.measure6,
                                            cocktail_info.measure7,
                                            cocktail_info.measure8,
                                    };

                                    for (int j = 0; j < 8; j++) {
                                        HashMap<String, String> hm = new HashMap<>();
                                        hm.put("Icons", Integer.toString(icons[j]));
                                        hm.put("Ingredients", ingredients[j]);
                                        hm.put("Measurements", measurements[j]);
                                        ingredientsList.add(hm);
                                    }

                                    String[] from = {"Icons", "Ingredients", "Measurements"};
                                    int[] to = {R.id.img, R.id.ingredient, R.id.measure};

                                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), ingredientsList, R.layout.list_item, from, to);
                                    ListView androidListView = findViewById(R.id.Ingredients_list);
                                    androidListView.setAdapter(simpleAdapter);
                                }
                            } catch (final JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Json parsing error: " + e.getMessage(),
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                });
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("debug", error.toString());
                    }
                });

        // add it to the RequestQueue
        requestQueue.add(jsonObjectRequest);

        //Favourite toggle button
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_border));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_border));
            }
        });

        //Number of servings
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    // Function to download IMG from URL
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


        /*new DownloadImageTask((ImageView) findViewById(R.id.img_recent1))
                    .execute(cocktailArrayList.get(0).image);
            ((TextView)findViewById(R.id.text_recent1)).setText(cocktailArrayList.get(0).name);*/

        private DownloadImageTask(ImageView bmImage) {
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