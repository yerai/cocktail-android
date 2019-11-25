package com.example.cocktail;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

import java.util.ArrayList;

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

        
        setContentView(R.layout.activity_details_view);

        textView = findViewById(R.id.txt_header);
        textView.setText("Bloody Mary");
        imageView = findViewById(R.id.img_cocktail_details);
        imageView.setImageResource(R.drawable.bloody_mary);
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
}