/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.util;

/**
 *
 * @author flash
 */
public class Location {
 
    private float lat;
    private float lon;
    
    public Location(float lat, float lon){
        this.lon = lon;
        this.lat = lat;
    }
    
    public float getLat(){
        return lat;
    }
    
    public String toString(){
        return "["+lon+","+lat+"]";
    }
    
    public float getLon(){
        return lon;
    }
}
