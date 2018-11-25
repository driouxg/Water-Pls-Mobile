package com.dryox.water_pls_mobile.domain;

public class GeographicLocation extends ValueObject {
    private String latitude;
    private String longitude;

    public GeographicLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
