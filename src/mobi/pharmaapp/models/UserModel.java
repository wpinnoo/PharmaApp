package mobi.pharmaapp.models;

import com.google.android.maps.GeoPoint;
import mobi.pharmaapp.util.Location;

/**
 *
 * @author see /AUTHORS
 */
public class UserModel {

    private GeoPoint curLoc;
    private static final UserModel userModel = new UserModel();

    private UserModel() {
        curLoc = new GeoPoint(51100607, 3763328);
    }

    public static UserModel getInstance() {
        return userModel;
    }

    public Location getCurrentLocation() {
        return new Location((float) curLoc.getLatitudeE6() / 1000000, (float) curLoc.getLongitudeE6() / 1000000);
    }

    public void setCurrentLocation(GeoPoint l) {
        curLoc = l;
    }

    public void setCurrentLocation(double lat, double lon) {
        curLoc = new GeoPoint((int) (lat * 1000000), (int) (lon * 1000000));
    }
}
