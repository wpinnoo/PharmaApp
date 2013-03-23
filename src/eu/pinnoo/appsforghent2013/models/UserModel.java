/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.models;

import eu.pinnoo.appsforghent2013.util.Location;

/**
 *
 * @author flash
 */
public class UserModel { 
    
    private Location curLoc;
    private static final UserModel userModel = new UserModel();
    
    private UserModel(){}
    
    public static UserModel getInstance(){
        return userModel;
    }
    
    public Location getCurrentLocation(){
        return curLoc;
    }
    
    public void setCurrentLocation(Location l){
        UserModel.getInstance().curLoc = curLoc;
    }
    
    public static void setCurrentLocation(float lat, float lon){
        UserModel.getInstance().curLoc = new Location(lat, lon);
    }
    
}
