package mobi.pharmaapp.util;

/**
 *
 * @author see /AUTHORS
 */
public class Pharmacy {

    private Location loc;
    private String name;
    private String address;
    private int dist;
    private String id;
    private String fid;
    private int zipcode;
    private String town;

    public Pharmacy(float lat, float lon, String name, String address, int dist, String id, String fid, int zipcode, String town) {
        this.loc = new Location(lat, lon);
        this.name = name;
        this.address = address;
        this.dist = dist;
        this.id = id;
        this.fid = fid;
        this.zipcode = zipcode;
        this.town = town;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public boolean matches(String s){
        s = s.toLowerCase();
        return name.toLowerCase().contains(s) || address.toLowerCase().contains(s)
                || ("" + zipcode).contains(s) || town.toLowerCase().contains(s);
    }

    public Location getLocation() {
        return loc;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getDist() {
        return dist;
    }

    public String getId() {
        return id;
    }

    public String getFid() {
        return fid;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getTown() {
        return town;
    }
}
