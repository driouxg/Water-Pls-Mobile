package com.dryox.water_pls_mobile.domain.value;

public class GeographicLocationVO extends ValueObject {
    private GeoCoordinateVO latitude;
    private GeoCoordinateVO longitude;

    public GeographicLocationVO(GeoCoordinateVO latitude, GeoCoordinateVO longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoCoordinateVO getLatitude() {
        return latitude;
    }

    public GeoCoordinateVO getLongitude() {
        return longitude;
    }
}
