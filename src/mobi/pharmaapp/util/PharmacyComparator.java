package mobi.pharmaapp.util;

import mobi.pharmaapp.models.UserModel;
import java.util.Comparator;

/**
 *
 * @author see /AUTHORS
 */
public class PharmacyComparator implements Comparator<Pharmacy> {

    public int compare(Pharmacy p1, Pharmacy p2) {
        float distP1 = p1.getLocation().distanceto(UserModel.getInstance().getCurrentLocation());
        float distP2 = p2.getLocation().distanceto(UserModel.getInstance().getCurrentLocation());

        return (int) (distP1 - distP2);
    }
}
