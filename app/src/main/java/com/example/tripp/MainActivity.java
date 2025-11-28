package com.example.tripp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerv;
    private CountryAdapter adap;
    private ArrayList<Country> countryList;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerv = findViewById(R.id.recyclerView);
        countryList = new ArrayList<>();

         countryList.add(new Country("France", R.drawable.france));
        countryList.add(new Country("Turkey", R.drawable.turkey));
        countryList.add(new Country("Egypt", R.drawable.egypt));
        countryList.add(new Country("Spain", R.drawable.spain));
        countryList.add(new Country("Palestine", R.drawable.palestinee));
        countryList.add(new Country("lebanon", R.drawable.lebanon));

        adap = new CountryAdapter(this, countryList);
        recyclerv.setLayoutManager(new LinearLayoutManager(this));

         recyclerv.setAdapter(adap);

        adap.setOnItemClickListener(position -> {
            Country selecCountry = countryList.get(position);
            Intent in = new Intent(MainActivity.this, CountryActivity.class);
            in.putExtra("countryName", selecCountry.getName());
            startActivity(in);
        });
    }
}
