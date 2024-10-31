package com.juliano.locationfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addNewButton = findViewById(R.id.addNewButton);

        addNewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemAddActivity.class);
                intent.putExtra("isEdit",false);
                startActivity(intent);
            }
        });

        searchView = (SearchView) findViewById(R.id.searchBar);
        searchView.setQueryHint("Search For Locations");
        searchView.setOnClickListener(v -> searchView.setIconified(false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHandler db = new DBHandler(this);
        Cursor cursor = db.getAllLocations();
        populateSpinner(db, cursor);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){

                }else{

                }
                return false;
            }
        });
    }

    private void populateSpinner(DBHandler db,Cursor cursor){

        LinearLayout locationCardsLayout = findViewById(R.id.card_inner);
        locationCardsLayout.removeAllViews();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                Location loc = new Location(id,address,latitude,longitude);
                LayoutInflater inflater = LayoutInflater.from(this);
                View locationCard = inflater.inflate(R.layout.location_card, locationCardsLayout, false);

                TextView addressText = locationCard.findViewById(R.id.address);
                TextView latText = locationCard.findViewById(R.id.lat);
                TextView longText = locationCard.findViewById(R.id.longitude);

                addressText.setText(address);
                latText.setText(String.valueOf(latitude));
                longText.setText(String.valueOf(longitude));

                Button infoButton = locationCard.findViewById(R.id.infoButton);
                infoButton.setOnClickListener(v -> {
                    Intent viewIntent = new Intent(MainActivity.this, ItemViewActivity.class);
                    viewIntent.putExtra("locationID", loc.getId());
                    viewIntent.putExtra("address", loc.getAddress());
                    viewIntent.putExtra("latitude", loc.getLatitude());
                    viewIntent.putExtra("longitude", loc.getLongitude());
                    startActivity(viewIntent);
                });

                Button editButton = locationCard.findViewById(R.id.editButton);
                editButton.setOnClickListener(v -> {
                    Intent editIntent = new Intent(MainActivity.this, ItemAddActivity.class);
                    editIntent.putExtra("locationID", loc.getId());
                    editIntent.putExtra("address", loc.getAddress());
                    editIntent.putExtra("latitude", loc.getLatitude());
                    editIntent.putExtra("longitude", loc.getLongitude());
                    editIntent.putExtra("isEdit",true);
                    startActivity(editIntent);
                });

                locationCardsLayout.addView(locationCard);

            } while (cursor.moveToNext());
        }
        cursor.close();

    }

}