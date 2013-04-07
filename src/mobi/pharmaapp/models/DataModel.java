package mobi.pharmaapp.models;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import mobi.pharmaapp.util.Pharmacy;
import java.util.HashMap;
import mobi.pharmaapp.util.PharmacyAlphComparator;
import mobi.pharmaapp.util.PharmacyComparator;

/**
 *
 * @author see /AUTHORS
 */
public class DataModel {

    public enum LIST_TYPE {
        NORMAL, SORT_ON_DISTANCE, SORT_ALPH;
    }
    
    public enum MAP_MODE {
        ALL, EMERGENCY_ONLY;
    }
    
    private HashMap<String, Pharmacy> pharmacies;
    private ArrayList<Pharmacy> em_pharmacies;
    private static final DataModel model = new DataModel();
    private Activity pharmacistsContainer;
    private Activity em_pharmacistsContainer;
    private Date lastUpdateEmergencyPharmacists;
    private MAP_MODE mapMode;

    private DataModel() {
        pharmacies = new HashMap<String, Pharmacy>();
        em_pharmacies = new ArrayList<Pharmacy>();
        mapMode = MAP_MODE.ALL;
    }
    
    public MAP_MODE getMapMode(){
        return mapMode;
    }
    
    public void setMapMode(MAP_MODE newMode){
        this.mapMode = newMode;
    }

    public Date getLastEmPharmsUpdate() {
        return lastUpdateEmergencyPharmacists;
    }

    public void setLastEmPharmsUpdate(Date date) {
        this.lastUpdateEmergencyPharmacists = date;
    }

    public void setPharmacistsContainer(Activity activity) {
        if (pharmacistsContainer == null) {
            pharmacistsContainer = activity;
        }
    }

    public Activity getPharmaciesContainer() {
        return pharmacistsContainer;
    }

    public void setEmergencyPharmacistsContainer(Activity activity) {
        if (em_pharmacistsContainer == null) {
            em_pharmacistsContainer = activity;
        }
    }

    public Activity getEmergencyPharmaciesContainer() {
        return em_pharmacistsContainer;
    }

    public static DataModel getInstance() {
        return model;
    }

    public HashMap<String, Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public ArrayList<Pharmacy> getEmergencyPharmacies(LIST_TYPE type) {
        ArrayList<Pharmacy> sorted;
        switch(type){
            case NORMAL:
                return em_pharmacies;
            case SORT_ALPH:
                sorted = new ArrayList<Pharmacy>();
                sorted.addAll(em_pharmacies);
                Collections.sort(sorted, new PharmacyAlphComparator());
                return sorted;
            case SORT_ON_DISTANCE:
                sorted = new ArrayList<Pharmacy>();
                sorted.addAll(em_pharmacies);
                Collections.sort(sorted, new PharmacyComparator());
                return sorted;
            default:
                return em_pharmacies;
        }
    }

    public Pharmacy getPharmacy(String id) {
        return pharmacies.get(id);
    }

    public void addPharmacy(Pharmacy a) {
        pharmacies.put(a.getId(), a);
    }

    public void addEmergencyPharmacy(Pharmacy a) {
        em_pharmacies.add(a);
    }

    public void resetEmergencyPharmacists() {
        em_pharmacies.clear();
    }

    public void resetPharmacists() {
        pharmacies.clear();
    }
}
