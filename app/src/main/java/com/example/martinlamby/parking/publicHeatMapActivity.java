package com.example.martinlamby.parking;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Revo on 9/15/2015.
 */
public class publicHeatMapActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private ArrayList<ParkedCarLocation> parkedCarLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_heat_map);

        parkedCarLocations = new ArrayList<>();


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public ArrayList<ParkedCarLocation> getAllParkedCarPositions() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkedCarPosition");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (int i = 0; i < list.size(); i++) {
                    double latitude = parseDoubleString(list.get(i).get("latitude").toString());
                    double longitude = parseDoubleString(list.get(i).get("longitude").toString());
                    ParkedCarLocation x = new ParkedCarLocation(latitude, longitude);
                    parkedCarLocations.add(x);
                }

            }
        });
        return parkedCarLocations;
    }

    public double parseDoubleString(String number) {
        int length = number.length();
        String x = number.substring(1, length - 1);
        return Double.valueOf(x);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getAllParkedCarPositions();


        ArrayList<LatLng> list = new ArrayList<LatLng>() {


        };
        for (int i = 0; i < parkedCarLocations.size(); i++) {
            list.add(new LatLng(parkedCarLocations.get(i).getLatitude(), parkedCarLocations.get(i).getLongitude()));

        }
        if (list.size() == 0) {
            list.add(new LatLng(GeoLocationService.getLastLocationLatitude(),
                    GeoLocationService.getLastLocationLongitude()));
        }


        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();

        TileOverlay mOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(GeoLocationService.getLastLocationLatitude(),
                        GeoLocationService.getLastLocationLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

    }


}