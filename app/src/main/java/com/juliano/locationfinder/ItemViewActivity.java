package com.juliano.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class ItemViewActivity extends AppCompatActivity {

    private MapView mapView;
    int id;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().setUserAgentValue("com.juliano.locationfinder");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DBHandler(this);

        //Get values from intent
        Intent intent = getIntent();
        id = intent.getIntExtra("locationID",-1);
        String address = intent.getStringExtra("address");
        double lati = intent.getDoubleExtra("latitude",0.0);
        double longi = intent.getDoubleExtra("longitude",0.0);

        TextView addressValue = findViewById(R.id.addressValue);
        TextView latValue = findViewById(R.id.latValue);
        TextView longValue = findViewById(R.id.longValue);

        addressValue.setText(address);
        latValue.setText(String.valueOf(lati));
        longValue.setText(String.valueOf(longi));

        //Set up Map Specific things
        mapView = findViewById(R.id.map);
        mapView.setMultiTouchControls(true);
        GeoPoint startPoint = new GeoPoint(lati, longi);
        mapView.getController().setZoom(17.0);
        mapView.getController().setCenter(startPoint);
        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker.setTitle(address);
        mapView.getOverlays().add(startMarker);

    }

    public void onBackClick(View v){
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }
    public void onDeleteClick(View v){
        db.deleteLocation(id);
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }


}