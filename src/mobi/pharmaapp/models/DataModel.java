package mobi.pharmaapp.models;

import mobi.pharmaapp.util.Pharmacy;
import java.util.HashMap;

/**
 *
 * @author see /AUTHORS
 */
public class DataModel {

    private HashMap<String, Pharmacy> pharmacies;
    private static final DataModel model = new DataModel();

    private DataModel() {
        pharmacies = new HashMap<String, Pharmacy>();
    }

    public static DataModel getInstance() {
        return model;
    }

    public HashMap<String, Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public Pharmacy getPharmacy(String id) {
        return pharmacies.get(id);
    }

    public void addPharmacy(Pharmacy a) {
        pharmacies.put(a.getId(), a);
    }
}