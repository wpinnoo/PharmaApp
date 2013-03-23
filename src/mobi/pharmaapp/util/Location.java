package mobi.pharmaapp.util;

import mobi.pharmaapp.models.UserModel;

/**
 *
 * @author see /AUTHORS
 */
public class Location {

    private float lat;
    private float lon;

    public Location(float lat, float lon) {
        this.lon = lon;
        this.lat = lat;
    }

    public float getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "[" + lon + "," + lat + "]";
    }

    public float getLon() {
        return lon;
    }

    public float distanceto(Location l) {
        return (float) (Math.sqrt(Math.pow(2, getLat() - UserModel.getInstance().getCurrentLocation().getLat())
                + Math.pow(2, getLon() - UserModel.getInstance().getCurrentLocation().getLon())));
    }
}
