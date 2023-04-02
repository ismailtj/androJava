package com.example.weatherappjava;

import static kotlinx.coroutines.BuildersKt.launch;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import models.ApiClient;
import models.Temp;
import retrofit2.Response;

public class AddPostionForm extends AppCompatActivity {

    private String tempsState;
    private EditText numbText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_position_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("Form");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        numbText= findViewById(R.id.editTextNumber);
        numbText.setText("0.0");


        Spinner spinner = findViewById(R.id.spinner);
        Map<String, String> opts = new HashMap<>();
        opts.put("Sunny", "");
        opts.put("Rainy", "");
        opts.put("Cloudy", "");
        opts.put("Snow", "");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(opts.keySet()));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> l = new ArrayList<>(opts.keySet());
                String selectedOption = l.get(position);
                tempsState = selectedOption;
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {

            Temp temp = new Temp();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            }

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Here you can get the latitude and longitude values from the Location object
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    temp.setLatitude(latitude);
                    temp.setLongitude(longitude);
                    Log.d("Location", "Latitude: " + latitude + " Longitude: " + longitude);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };


            temp.setTemps(tempsState);
            temp.setTemperature(Double.parseDouble(String.valueOf(numbText.getText())));
            addTemp(temp);
        });

    }


    private void addTemp(Temp tmp) {
            try {
                Response<Temp> resp = ApiClient.getApiService().createTemp(tmp).execute();
                if (resp.isSuccessful() && resp.body() != null) {
                    Temp content = resp.body();
                    Toast.makeText(
                            AddPostionForm.this,
                            "Added successfully",
                            Toast.LENGTH_LONG
                    ).show();
                    //finish();
                } else {
                    Toast.makeText(
                            AddPostionForm.this,
                            "Error Occurred: " + resp.message(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            } catch (Exception e) {
                Toast.makeText(
                        AddPostionForm.this,
                        "Error Occurred: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
