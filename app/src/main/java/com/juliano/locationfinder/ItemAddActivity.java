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

        EditText addressEdit = findViewById(R.id.addressInput);
        EditText latEdit = findViewById(R.id.latitudeInput);
        EditText longEdit = findViewById(R.id.longInput);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit",false);
        if(isEdit){
            id = intent.getIntExtra("locationID",-1);
            String address = intent.getStringExtra("address");
            double lati = intent.getDoubleExtra("latitude",0.0);
            double longi = intent.getDoubleExtra("longitude",0.0);

            addressEdit.setText(address);
            latEdit.setText(String.valueOf(lati));
            longEdit.setText(String.valueOf(longi));
        }

        Button addButton = findViewById(R.id.addButton);
        Button updateButton = findViewById(R.id.updateButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}