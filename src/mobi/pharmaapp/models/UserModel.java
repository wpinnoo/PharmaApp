package mobi.pharmaapp.models;

import mobi.pharmaapp.util.Location;

/**
 *
 * @author see /AUTHORS
 */
public class UserModel {

    private Location curLoc;
    private static final UserModel userModel = new UserModel();

    private UserModel() {
        //TODO: DELETE CONSTANT VALUE
        curLoc = new Location((float) 51.1006070515313, (float) 3.76332831384537);
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
