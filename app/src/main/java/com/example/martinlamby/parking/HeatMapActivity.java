package com.example.martinlamby.parking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HeatMapActivity extends AppCompatActivity {


    public ArrayList<ParkedCarLocation> privateParkedCarLocations= new ArrayList<>();
    public ArrayList<ParkedCarLocation> publicParkedCarLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map);
     getPrivateParkedCarPositions();
     getPublicParkedcarPositions();

        System.out.println( "PRIVATE:"+privateParkedCarLocations.size());
        System.out.println( "PUBLIC:"+publicParkedCarLocations.size());

        Button privateHeatmap = (Button) findViewById(R.id.privateHeatMapButton);
        Button publicHeatmap = (Button) findViewById(R.id.publicHeatMapButton);

        publicHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent heatmapIntent = (new Intent(getApplicationContext(), displayHeatMapActivity.class));

                heatmapIntent.putExtra("test", publicParkedCarLocations);

                startActivity(heatmapIntent);
            }
        });


        privateHeatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent heatmapIntent = (new Intent(getApplicationContext(), displayHeatMapActivity.class));

                heatmapIntent.putExtra("test", privateParkedCarLocations);

                startActivity(heatmapIntent);
            }
        });

    }

    public ArrayList<ParkedCarLocation> getPublicParkedcarPositions() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkedCarPosition");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (int i = 0; i < list.size(); i++) {

                    double latitude = parseDoubleString(list.get(i).get("latitude").toString());
                    double longitude = parseDoubleString(list.get(i).get("longitude").toString());

                    ParkedCarLocation x = new ParkedCarLocation(latitude, longitude);
                    publicParkedCarLocations.add(x);

                }

            }
        });

        return publicParkedCarLocations;
    }






    public ArrayList<ParkedCarLocation> getPrivateParkedCarPositions() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkedCarPosition");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername().toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (int i = 0; i < list.size(); i++) {

                    double latitude = parseDoubleString(list.get(i).get("latitude").toString());
                    double longitude = parseDoubleString(list.get(i).get("longitude").toString());

                    ParkedCarLocation x = new ParkedCarLocation(latitude, longitude);
                    privateParkedCarLocations.add(x);

                }

            }
        });

        return privateParkedCarLocations;
    }
    public double parseDoubleString(String number) {
        int length = number.length();
        String x = number.substring(1, length - 1);
        return Double.valueOf(x);
    }





}
