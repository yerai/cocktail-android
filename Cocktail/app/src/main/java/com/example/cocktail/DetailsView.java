package com.example.cocktail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
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
import java.util.Iterator;

public class DetailsView extends AppCompatActivity {

    public static final String TAG = "ListViewExample";

    private ListView listView;

    TextView textView;
    ImageView imageView;
    ToggleButton toggleButton;
    Spinner spinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Top Bar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // Get ID from View
        String id = getIntent().getStringExtra("id");

        // Data structure of cocktail
        final cocktail cocktail_info = new cocktail();

        // Get Data of Cocktail
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        Iterator<?> keys = response.keys();
                        while(keys.hasNext() ) {
                            String key = (String)keys.next();
                            try {
                                JSONArray drinks = new JSONArray(response.get(key).toString());

                                for(int i=0; i<drinks.length(); i++){
                                    final JSONObject drink = drinks.getJSONObject(i);

                                    cocktail_info.name = drink.getString("strDrink");
                                    cocktail_info.image = drink.getString("strDrinkThumb");
                                    cocktail_info.glass = drink.getString("strGlass");
                                    cocktail_info.instructions = drink.getString("strInstructions");
                                    cocktail_info.ingredient1 = drink.getString("strIngredient1");
                                    cocktail_info.measure1 = drink.getString("strMeasure1");

                                    // Set View
                                    // Title
                                    textView = findViewById(R.id.txt_header);
                                    textView.setText(cocktail_info.name);

                                    // Image
                                    ImageView iv = (ImageView) findViewById(R.id.img_cocktail_details);
                                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, iv.getWidth()));
                                    iv.setLayoutParams(params4);
                                    new DetailsView.DownloadImageTask(iv).execute(cocktail_info.image);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

        setContentView(R.layout.activity_details_view);

        spinner = findViewById(R.id.servings_spinner);
        toggleButton = findViewById(R.id.myToggleButton);
        listView = findViewById(R.id.Cocktails_Ingredients_list);
        button = findViewById(R.id.btn_share);

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

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                UserAccount user = (UserAccount)listView.getItemAtPosition(position);
                user.setActive(!currentCheck);
            }
        });
        UserAccount vodka = new UserAccount("vodka","1/2 oz", false);
        UserAccount lemon = new UserAccount("Lemon juice","1 dash", false);
        UserAccount tomato = new UserAccount("Tomato juice","2 dash", false);
        UserAccount worcestershire = new UserAccount(" Worcestershire Sauce","1/2 tsp", false);
        UserAccount lime = new UserAccount("Lime","1 wedge", false);
        UserAccount tobasco = new UserAccount("Tobasco sauce","2 drops", false);

        UserAccount[] users = new UserAccount[]{vodka,lemon, tomato, worcestershire, lime, tobasco};

        ArrayAdapter<UserAccount> arrayAdapter2
                = new ArrayAdapter<UserAccount>(this, android.R.layout.simple_list_item_multiple_choice , users);


        listView.setAdapter(arrayAdapter2);

        for(int i=0;i< users.length; i++ )  {
            listView.setItemChecked(i,users[i].isActive());
        }
    }

    public void printSelectedItems(View view)  {

        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();

        for(int i=0;i<sp.size();i++){
            if(sp.valueAt(i)==true){
                UserAccount user= (UserAccount) listView.getItemAtPosition(i);
                // Or:
                // String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                String s= user.getUserName();
                sb = sb.append(" "+s);
            }
        }
        Toast.makeText(this, "Selected items are: "+sb.toString(), Toast.LENGTH_LONG).show();

    }

    public void myClickHandler(View target) {
        printSelectedItems(listView);
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