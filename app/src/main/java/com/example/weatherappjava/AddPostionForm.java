package com.example.weatherappjava;

import static kotlinx.coroutines.BuildersKt.launch;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import models.ApiClient;
import models.Temp;
import retrofit2.Call;
import retrofit2.Response;

public class AddPostionForm extends AppCompatActivity {

    private String tempsState;
    private Temp temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_position_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("Form");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setTemps(tempsState);
                temp.setLongitude(0.0);
                temp.setLatitude(0.0);
                temp.setTemperature(0.0);
                addTemp(temp);
            }
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
