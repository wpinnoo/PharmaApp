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
    private String telnr;

    public Pharmacy(float lat, float lon, String name, String address, int dist, String id, String fid, int zipcode, String town) {
        this.loc = new Location(lat, lon);
        this.name = name;
        this.address = address;
        this.dist = dist;
        this.id = id;
        this.fid = fid;
        this.zipcode = zipcode;
        this.town = town;
        telnr = "";
    }

    public Pharmacy(float lat, float lon, String name, String address, int dist, String id, String fid, int zipcode, String town, String telnr) {
        this(lat, lon, name, address, dist, id, fid, zipcode, town);
        this.setTelnr(telnr);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean matches(String s) {
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

    public String getTelnr() {
        return telnr;
    }

    public void setTelnr(String telnr) {
        this.telnr = telnr;
    }

    public static String beautifyName(String name) {
        String result = "";
        for (String subname : name.split("\n")[0].split(" ")) {
            char letter = subname.charAt(0);
            subname = subname.toLowerCase();
            result += letter + subname.substring(1, subname.length()) + " ";
        }
        if (result.contains("-")) {
            String finalresult = "";
            for (String subname : result.split("\n")[0].split("-")) {
                subname = subname.trim();
                String letter = ("" + subname.charAt(0)).toUpperCase();
                subname = subname.toLowerCase();
                finalresult += letter + subname.substring(1, subname.length()) + "-";
            }
            finalresult = finalresult.trim();
            return finalresult.substring(0, finalresult.length()-1);
        } else {
            return result.trim();
        }
    }
}
