package com.example.tripp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private ArrayList<TripActivity> activityList;
    private ArrayList<TripActivity> displayedList;
    private EditText searchBox;
    private ImageButton addBtn;
    private SharedPreferences sharedPreferences;
    private String countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryName = getIntent().getStringExtra("countryName");
        sharedPreferences = getSharedPreferences("TripPrefs_" + countryName, MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerView);
        searchBox = findViewById(R.id.search_box);
        addBtn = findViewById(R.id.add_button);

        activityList = new ArrayList<>();
        displayedList = new ArrayList<>();

        loadData();

        displayedList.addAll(activityList);
        adapter = new TripAdapter(this, displayedList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            TripActivity selected = displayedList.get(position);
            Intent intent = new Intent(CountryActivity.this, AddActivity.class);
            intent.putExtra("activityData", selected);
            intent.putExtra("position", activityList.indexOf(selected));
            intent.putExtra("countryName", countryName);
            startActivity(intent);
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence ss, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence ss, int start, int before, int count) {
                filterr(searchBox.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CountryActivity.this, AddActivity.class);
            intent.putExtra("countryName", countryName);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        filterr(searchBox.getText().toString());
    }

    private void filterr(String text) {
        displayedList.clear();
        String qu = text.toLowerCase();

        for (TripActivity item : activityList) {
            String name = item.getName().toLowerCase();
            String type = item.getType().toLowerCase();

            if (name.contains(qu) || type.contains(qu)) {
                displayedList.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }


    private void loadData() {
        activityList.clear();
        int size = sharedPreferences.getInt("activity_size", 0);

        for (int i = 0; i < size; i++) {
            String name = sharedPreferences.getString("activity_" + i + "_name", "");
            String type = sharedPreferences.getString("activity_" + i + "_type", "");
            String date = sharedPreferences.getString("activity_" + i + "_date", "");
            String time = sharedPreferences.getString("activity_" + i + "_time", "");
            boolean reservation = sharedPreferences.getBoolean("activity_" + i + "_reservation", false);
            String priority = sharedPreferences.getString("activity_" + i + "_priority", "");
            String weather = sharedPreferences.getString("activity_" + i + "_weather", "");

            activityList.add(new TripActivity(name, type, date, time, reservation, priority, weather));
        }
    }
}
