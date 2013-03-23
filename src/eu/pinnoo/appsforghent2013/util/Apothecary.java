/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.util;

/**
 *
 * @author flash
 */
public class Apothecary {

    private Location loc;
    private String name;
    private String address;
    private int dist;
    private String id;
    private String fid;
    private int postcode;
    private String gemeente;

    public Apothecary(float lat, float lon, String name, String address, int dist, String id, String fid, int postcode, String gemeente) {
        this.loc = new Location(lat, lon);
        this.name = name;
        this.address = address;
        this.dist = dist;
        this.id = id;
        this.fid = fid;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }
    
    public String toString(){
        return "Apothecary "+name + " address="+address+" loc="+loc+" id="+id+" zip="+postcode+" gemeente="+gemeente;
    }

    public Location getLocation() {
        return loc;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the dist
     */
    public int getDist() {
        return dist;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the fid
     */
    public String getFid() {
        return fid;
    }

    /**
     * @return the postcode
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * @return the gemeente
     */
    public String getGemeente() {
        return gemeente;
    }
}
