package com.example.cocktail;

import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

    // Log tag
    public static final String TAG = DetailsView.class.getSimpleName();

    // Movies json url
    private ProgressDialog pDialog;

    TextView textView;
    ToggleButton toggleButton;
    Button button;

    ArrayList<HashMap<String, String>> ingredientsList;
    String[] ingredients;
    String[] measurements;
    ArrayList<String> shoppingList = new ArrayList<>();
    ArrayList<String> shoppingList2 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Hide Top Bar
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        // Get ID from View
        final String id = getIntent().getStringExtra("id");
        // Get Data of Cocktail
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id;

        toggleButton = findViewById(R.id.myToggleButton);
        ingredientsList = new ArrayList<>();
        final ListView ingredientsListview = findViewById(R.id.Ingredients_list);

        // Data structure of cocktail
        final cocktail cocktail_info = new cocktail();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
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

                                    Log.d("Hello",cocktail_info.ingredient3);

                                    // Set View
                                    // Title
                                    textView = findViewById(R.id.txt_header);
                                    textView.setText(cocktail_info.name);

                                    // Image
                                    ImageView iv = findViewById(R.id.img_cocktail_details);
                                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, iv.getWidth()));
                                    iv.setLayoutParams(params4);
                                    new DownloadImageTask(iv).execute(cocktail_info.image);

                                    // Glass text
                                    textView = findViewById(R.id.txt_glass);
                                    textView.setText(cocktail_info.glass);

                                    // Instructions
                                    textView = findViewById(R.id.txt_instructions);
                                    textView.setText(cocktail_info.instructions);

                                    String[] imageURLArray = new String[]{
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient1 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient2 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient3 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient4 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient5 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient6 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient7 + "-Small.png",
                                            "https://www.thecocktaildb.com/images/ingredients/" + cocktail_info.ingredient8 + "-Small.png"
                                    };

                                    ImageView imgIngredient1 = findViewById(R.id.img_ingredient1);
                                    ImageView imgIngredient2 = findViewById(R.id.img_ingredient2);
                                    ImageView imgIngredient3 = findViewById(R.id.img_ingredient3);
                                    ImageView imgIngredient4 = findViewById(R.id.img_ingredient4);
                                    ImageView imgIngredient5 = findViewById(R.id.img_ingredient5);
                                    ImageView imgIngredient6 = findViewById(R.id.img_ingredient6);
                                    ImageView imgIngredient7 = findViewById(R.id.img_ingredient7);
                                    ImageView imgIngredient8 = findViewById(R.id.img_ingredient8);

                                    ingredients = new String[8];

                                    if (cocktail_info.ingredient1.equals("null") || cocktail_info.ingredient1.equals("")) {
                                        imgIngredient1.setVisibility(View.GONE);
                                        ingredients[0] = "null";
                                    } else {
                                        Log.d("TEST",cocktail_info.ingredient1);
                                        ingredients[0] = cocktail_info.ingredient1;
                                        shoppingList.add(cocktail_info.ingredient1);
                                        new DownloadImageTask(imgIngredient1).execute(imageURLArray[0]);
                                    }
                                    if (cocktail_info.ingredient2.equals("null" ) || cocktail_info.ingredient2.equals("")) {
                                        imgIngredient2.setVisibility(View.GONE);
                                        ingredients[1] = "null";
                                    } else {
                                        ingredients[1] = cocktail_info.ingredient2;
                                        shoppingList.add(cocktail_info.ingredient2);
                                        new DownloadImageTask(imgIngredient2).execute(imageURLArray[1]);
                                    }
                                    if (cocktail_info.ingredient3.equals("null") || cocktail_info.ingredient3.equals("")) {
                                        Log.d("Hello","1");
                                        imgIngredient3.setVisibility(View.GONE);
                                        ingredients[2] = "null";
                                    } else {
                                        Log.d("Hello","2");
                                        ingredients[2] = cocktail_info.ingredient3;
                                        shoppingList.add(cocktail_info.ingredient3);
                                        new DownloadImageTask(imgIngredient3).execute(imageURLArray[2]);
                                    }
                                    if (cocktail_info.ingredient4.equals("null") || cocktail_info.ingredient4.equals("")) {
                                        imgIngredient4.setVisibility(View.GONE);
                                        ingredients[3] = "null";
                                    } else {
                                        ingredients[3] = cocktail_info.ingredient4;
                                        shoppingList.add(cocktail_info.ingredient4);
                                        new DownloadImageTask(imgIngredient4).execute(imageURLArray[3]);
                                    }
                                    if (cocktail_info.ingredient5.equals("null") || cocktail_info.ingredient5.equals("")) {
                                        imgIngredient5.setVisibility(View.GONE);
                                        ingredients[4] = "null";
                                    } else {
                                        ingredients[4] = cocktail_info.ingredient5;
                                        shoppingList.add(cocktail_info.ingredient5);
                                        new DownloadImageTask(imgIngredient5).execute(imageURLArray[4]);
                                    }
                                    if (cocktail_info.ingredient6.equals("null") || cocktail_info.ingredient6.equals("")) {
                                        imgIngredient6.setVisibility(View.GONE);
                                        ingredients[5] = "null";
                                    } else {
                                        ingredients[5] = cocktail_info.ingredient6;
                                        shoppingList.add(cocktail_info.ingredient6);
                                        new DownloadImageTask(imgIngredient6).execute(imageURLArray[5]);
                                    }
                                    if (cocktail_info.ingredient7.equals("null") || cocktail_info.ingredient7.equals("")) {
                                        imgIngredient7.setVisibility(View.GONE);
                                        ingredients[6] = "null";
                                    } else {
                                        ingredients[6] = cocktail_info.ingredient7;
                                        shoppingList.add(cocktail_info.ingredient7);
                                        new DownloadImageTask(imgIngredient7).execute(imageURLArray[6]);
                                    }
                                    if (cocktail_info.ingredient8.equals("null") || cocktail_info.ingredient8.equals("")) {
                                        ingredients[7] = "null";
                                        imgIngredient8.setVisibility(View.GONE);
                                    } else {
                                        ingredients[7] = cocktail_info.ingredient8;
                                        shoppingList.add(cocktail_info.ingredient8);
                                        new DownloadImageTask(imgIngredient8).execute(imageURLArray[7]);
                                    }

                                    measurements = new String[8];

                                    if (cocktail_info.measure1.equals("null") || cocktail_info.measure1.equals("")) {
                                        measurements[0] = "null";
                                        if(ingredients[0].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[0] = cocktail_info.measure1;
                                        shoppingList2.add(cocktail_info.measure1);
                                    }
                                    if (cocktail_info.measure2.equals("null") || cocktail_info.measure2.equals("")) {
                                        measurements[1] = "null";
                                        if(ingredients[1].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[1] = cocktail_info.measure2;
                                        shoppingList2.add(cocktail_info.measure2);
                                    }
                                    if (cocktail_info.measure3.equals("null") || cocktail_info.measure3.equals("")) {
                                        measurements[2] = "null";
                                        if(ingredients[2].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[2] = cocktail_info.measure3;
                                        shoppingList2.add(cocktail_info.measure3);
                                    }
                                    if (cocktail_info.measure4.equals("null") || cocktail_info.measure4.equals("")) {
                                        measurements[3] = "null";
                                        if(ingredients[3].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[3] = cocktail_info.measure4;
                                        shoppingList2.add(cocktail_info.measure4);
                                    }
                                    if (cocktail_info.measure5.equals("null") || cocktail_info.measure5.equals("")) {
                                        measurements[4] = "null";
                                        if(ingredients[4].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[4] = cocktail_info.measure5;
                                        shoppingList2.add(cocktail_info.measure5);
                                    }
                                    if (cocktail_info.measure6.equals("null") || cocktail_info.measure6.equals("")) {
                                        measurements[5] = "null";
                                        if(ingredients[5].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[5] = cocktail_info.measure6;
                                        shoppingList2.add(cocktail_info.measure6);
                                    }
                                    if (cocktail_info.measure7.equals("null") || cocktail_info.measure7.equals("")) {
                                        measurements[6] = "null";
                                        if(ingredients[6].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[6] = cocktail_info.measure7;
                                        shoppingList2.add(cocktail_info.measure7);
                                    }
                                    if (cocktail_info.measure8.equals("null") || cocktail_info.measure8.equals("")) {
                                        measurements[7] = "null";
                                        if(ingredients[7].equals("null")) {
                                        } else shoppingList2.add("To your liking");
                                    } else {
                                        measurements[7] = cocktail_info.measure8;
                                        shoppingList2.add(cocktail_info.measure8);
                                    }


                                    for (int j = 0; j < ingredients.length; j++) {
                                        if (ingredients[j] == "null") {
                                        } else {
                                            HashMap<String, String> hm = new HashMap<>();
                                            hm.put("Ingredients", ingredients[j]);
                                            if(measurements[j]=="null"){
                                                hm.put("Measurements", "To your liking");
                                            }else{
                                                hm.put("Measurements", measurements[j]);
                                            }
                                            ingredientsList.add(hm);
                                        }
                                    }

                                    String[] from = {"Ingredients", "Measurements"};
                                    int[] to = {R.id.ingredient, R.id.measurement};

                                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), ingredientsList, R.layout.list_item, from, to);
                                    ingredientsListview.setAdapter(simpleAdapter);


                                    //perform listView item click event
                                    ingredientsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                            View v = getViewByPosition(i,ingredientsListview);
                                            TextView t = v.findViewById(R.id.ingredient);
                                            TextView t2 = v.findViewById(R.id.measurement);
                                            if ((t.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0){
                                                t.setPaintFlags(0);
                                                t.setTextColor(Color.parseColor("#212529"));
                                                Toast.makeText(getApplicationContext(),"Added " + t.getText().toString()+" to shopping list",Toast.LENGTH_LONG).show();
                                                shoppingList.add(t.getText().toString());
                                                shoppingList2.add(t2.getText().toString());
                                            }else{
                                                t.setPaintFlags(t.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                                t.setTextColor(Color.parseColor("#b0b0b0"));
                                                for(int j = 0; j<shoppingList.size(); j++){
                                                    if(shoppingList.get(j).equals(t.getText().toString())){
                                                        Toast.makeText(getApplicationContext(),"Removed " + t.getText().toString() +" from shopping list",Toast.LENGTH_LONG).show();
                                                        shoppingList.remove(j);
                                                        shoppingList2.remove(j);
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    setListViewHeightBasedOnChildren(ingredientsListview);
                                }
                            } catch (final JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                hidePDialog();
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

                        //Favourite toggle button
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (MainActivity.checkFavorite(id) == true) {
                                toggleButton.setChecked(true);
                                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill));
                            } else {
                                toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_border));
                                toggleButton.setChecked(false);
                            }
                        }

                        //toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_border));
                        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill));
                                    MainActivity.addFavorite(id, cocktail_info.name, cocktail_info.image);
                                } else {
                                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_border));
                                    MainActivity.removeFavoriteByID(id);
                                }
                            }
                        });

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("debug", error.toString());
                    }
                });

        // add it to the RequestQueue
        requestQueue.add(jsonObjectRequest);

        button = findViewById(R.id.btn_share);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final StringBuilder ingredientsBuilder = new StringBuilder();
                for(int i = 0; i < shoppingList.size(); i++){
                    ingredientsBuilder.append(shoppingList2.get(i) + " of " + shoppingList.get(i) + "\n");
                }

                final String shareIngredients = ingredientsBuilder.toString();

                Intent intent = new Intent ();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "List of ingredients:");
                intent.putExtra(Intent.EXTRA_TEXT, shareIngredients);
                intent.setType("text/plain");
                startActivity(
                        Intent.createChooser(
                                intent,
                                getResources().getString(R.string.Ingredients)
                        )
                );
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    // Function to download IMG from URL
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

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

    // DPS to Pixels Function
    private int DPS(int dps) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}