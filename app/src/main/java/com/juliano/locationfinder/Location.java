package com.juliano.locationfinder;

public class Location {
    private int id;
    private String address;
    private double latitude;
    private double longitude;

    public Location(){
        this.id = -1;
        this.address = null;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
    public Location(String address, double lat, double longi){
        this.id = -1;
        this.address = address;
        this.latitude = lat;
        this.longitude = longi;
    }
    public Location(int id, String address, double lat, double longi){
        this.id = id;
        this.address = address;
        this.latitude = lat;
        this.longitude = longi;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
