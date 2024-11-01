package com.juliano.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ItemAddActivity extends AppCompatActivity {

    boolean isEdit;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBHandler db = new DBHandler(this);

        EditText addressEdit = (EditText) findViewById(R.id.addressInput);
        EditText latEdit = (EditText) findViewById(R.id.latitudeInput);
        EditText longEdit = (EditText) findViewById(R.id.longInput);
        Button addOrUpdateButton = (Button) findViewById(R.id.specialButton);

        // Get the intent data to determine if this is an edit or add operation
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit",false);
        if(isEdit){
            // Extract location data from the intent for editing
            id = intent.getIntExtra("locationID",-1);
            String address = intent.getStringExtra("address");
            double lati = intent.getDoubleExtra("latitude",0.0);
            double longi = intent.getDoubleExtra("longitude",0.0);

            // Pre-fill the fields with existing location data
            addressEdit.setText(address);
            latEdit.setText(String.valueOf(lati));
            longEdit.setText(String.valueOf(longi));

            // Set button text to "Update" and handle click for updating the location
            addOrUpdateButton.setText("Update");
            addOrUpdateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Update the location in the database
                    Location loc = new Location(id,
                                                addressEdit.getText().toString(),
                                                Double.parseDouble(latEdit.getText().toString()),
                                                Double.parseDouble(longEdit.getText().toString())
                                                );
                    db.updateLocation(loc);
                    Intent back = new Intent(ItemAddActivity.this, MainActivity.class);// Return to the main activity
                    startActivity(back);
                    finish();

                }
            });

        }else{
            // Set button text to "Add" and handle click for adding a new location
            addOrUpdateButton.setText("Add");
            addOrUpdateButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Add the new location to the database
                    Location loc = new Location(id,
                            addressEdit.getText().toString(),
                            Double.parseDouble(latEdit.getText().toString()),
                            Double.parseDouble(longEdit.getText().toString())
                    );
                    db.addLocation(loc);
                    Intent back = new Intent(ItemAddActivity.this, MainActivity.class);// Return to the main activity
                    startActivity(back);
                    finish();
                }
            });
        }


    }
}