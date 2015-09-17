package com.example.martinlamby.parking;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by martinlamby on 16.09.15.
 */
public class ParkedCarLocation implements Serializable, Parcelable{

    private double latitude;
    private double longitude;
    private String username;

    public ParkedCarLocation(double latitude, double longitude, String username) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
    }

    public ParkedCarLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected ParkedCarLocation(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        username = in.readString();
    }

    public static final Creator<ParkedCarLocation> CREATOR = new Creator<ParkedCarLocation>() {
        @Override
        public ParkedCarLocation createFromParcel(Parcel in) {
            return new ParkedCarLocation(in);
        }

        @Override
        public ParkedCarLocation[] newArray(int size) {
            return new ParkedCarLocation[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(username);
    }
}
