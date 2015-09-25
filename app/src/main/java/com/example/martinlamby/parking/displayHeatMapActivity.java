package com.example.martinlamby.parking;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;


/**
 * Created by Revo on 9/15/2015.
 */
public class displayHeatMapActivity extends FragmentActivity
        implements OnMapReadyCallback {
    GoogleMap map;
    ArrayList<ParkedCarLocation> parkedCarLocations = new ArrayList<>();
    ArrayList<LatLng> locList = new ArrayList<LatLng>() {


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_heat_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();

        parkedCarLocations = intent.getExtras().getParcelableArrayList("test");


    }

    private void addHeatMap() {


        for (int i = 0; i < parkedCarLocations.size(); i++) {
            locList.add(new LatLng(parkedCarLocations.get(i).getLatitude(), parkedCarLocations.get(i).getLongitude()));

        }
        if (locList.size() == 0) {
            locList.add(new LatLng(GeoLocationService.getLastLocationLatitude(),
                    GeoLocationService.getLastLocationLongitude()));
        }


        int[] colors = {
                Color.rgb(102, 225, 0),
                Color.rgb(255, 0, 0)
        };

        float[] startingPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startingPoints);


        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(locList)
                .gradient(gradient)
                .build();


        TileOverlay mOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        addHeatMap();


        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(GeoLocationService.getLastLocationLatitude(),
                        GeoLocationService.getLastLocationLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

    }


}
