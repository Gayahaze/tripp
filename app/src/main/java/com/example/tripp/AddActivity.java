package com.example.tripp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText nameE, dateE, timeE;
    private RadioGroup typeG;
    private CheckBox reservationCh;
    private Spinner prioritySp, weatherSp;
    private Button saveBtn;
    private SharedPreferences sharedPref;

    private String[] priorities = {"High", "Medium", "Low"};
    private String[] weatherOptions = {"Sunny", "Cloudy", "Rainy", "Windy"};
    private int position = -1;

    private String countryName;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        countryName = getIntent().getStringExtra("countryName");

        sharedPref = getSharedPreferences("TripPrefs_" + countryName, MODE_PRIVATE);

        nameE = findViewById(R.id.edit_name);
        dateE = findViewById(R.id.edit_date);
        timeE = findViewById(R.id.edit_time);
        typeG = findViewById(R.id.radio_group_type);
        reservationCh = findViewById(R.id.checkbox_reservation);
        prioritySp = findViewById(R.id.spinner_priority);
        weatherSp = findViewById(R.id.spinner_weather);
        saveBtn = findViewById(R.id.save_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySp.setAdapter(adapter);

        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weatherOptions);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSp.setAdapter(weatherAdapter);

        dateE.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();

            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

                dateE.setText(selectedDate);

                 dateE.setError(null);

            },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        });


        timeE.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                timeE.setText(String.format("%d:%02d", hourOfDay, minute));
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        TripActivity activity = (TripActivity) getIntent().getSerializableExtra("activityData");
        position = getIntent().getIntExtra("position", -1);

        if (activity != null) {
            nameE.setText(activity.getName());
            dateE.setText(activity.getDate());
            timeE.setText(activity.getTime());
            reservationCh.setChecked(activity.isNeedsReservation());

            switch (activity.getType()) {
                case "Beach":
                    typeG.check(R.id.radio_beach);
                    break;
                case "Hiking":
                    typeG.check(R.id.radio_Hiking);
                    break;
                case "Shopping":
                    typeG.check(R.id.radio_shopping);
                    break;
                case "Museum":
                    typeG.check(R.id.radio_museum);
                    break;
            }

            int spinnerPosition = ((ArrayAdapter) prioritySp.getAdapter()).getPosition(activity.getPriority());
            prioritySp.setSelection(spinnerPosition);

            int weatherPosition = ((ArrayAdapter) weatherSp.getAdapter()).getPosition(activity.getWeather());
            weatherSp.setSelection(weatherPosition);
        }

        saveBtn.setOnClickListener(v -> saveActivity());
    }

    private void saveActivity() {
        String name = nameE.getText().toString().trim();
        String date = dateE.getText().toString().trim();
        String time = timeE.getText().toString().trim();
        int selectedId = typeG.getCheckedRadioButtonId();
        String type = ((RadioButton) findViewById(selectedId)).getText().toString();
        boolean reservation = reservationCh.isChecked();
        String priority = prioritySp.getSelectedItem().toString();
        String weather = weatherSp.getSelectedItem().toString();

        if (name.isEmpty()) {
            nameE.setError("Please enter a name");
            return;
        }

        if (date.isEmpty()) {
            dateE.setError("Please select a date");
            return;
        }

         dateE.setError(null);

        try {
             String[] par = date.split("/");
            int day = Integer.parseInt(par[0]);
            int month = Integer.parseInt(par[1]) - 1;
             int year = Integer.parseInt(par[2]);

            Calendar seldate = Calendar.getInstance();
            seldate.set(year, month, day, 0, 0, 0);
            seldate.set(Calendar.MILLISECOND, 0);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            if (seldate.before(today)) {
                dateE.setError("Date cannot be in the past");
                return;
            }

        } catch (Exception e) {
            dateE.setError("Invalid date format");
            return;
        }


        if (time.isEmpty()) {
            timeE.setError("Please select a time");
            return;
        }

        if (selectedId == -1) {
            Toast.makeText(this, "Please select a type", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPref.edit();

        if (position != -1) {
            editor.putString("activity_" + position + "_name", name);
            editor.putString("activity_" + position + "_type", type);
            editor.putString("activity_" + position + "_date", date);
            editor.putString("activity_" + position + "_time", time);
            editor.putBoolean("activity_" + position + "_reservation", reservation);
            editor.putString("activity_" + position + "_priority", priority);
            editor.putString("activity_" + position + "_weather", weather);
        } else {
            int size = sharedPref.getInt("activity_size", 0);
            editor.putString("activity_" + size + "_name", name);
            editor.putString("activity_" + size + "_type", type);
            editor.putString("activity_" + size + "_date", date);
            editor.putString("activity_" + size + "_time", time);
            editor.putBoolean("activity_" + size + "_reservation", reservation);
            editor.putString("activity_" + size + "_priority", priority);
            editor.putString("activity_" + size + "_weather", weather);
            editor.putInt("activity_size", size + 1);
        }

        editor.apply();
        finish();
    }
}
