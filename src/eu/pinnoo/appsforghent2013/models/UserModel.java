package eu.pinnoo.appsforghent2013.models;

import eu.pinnoo.appsforghent2013.util.Location;

/**
 *
 * @author see /AUTHORS
 */
public class UserModel {

    private Location curLoc;
    private static final UserModel userModel = new UserModel();

    private UserModel() {
    }

    public static UserModel getInstance() {
        return userModel;
    }

    public Location getCurrentLocation() {
        return curLoc;
    }

    public void setCurrentLocation(Location l) {
        curLoc = l;
    }

    public void setCurrentLocation(float lat, float lon) {
        curLoc = new Location(lat, lon);
    }
}
