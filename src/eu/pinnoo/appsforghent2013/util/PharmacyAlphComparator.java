package eu.pinnoo.appsforghent2013.util;

import java.util.Comparator;

/**
 *
 * @author see /AUTHORS
 */
public class PharmacyAlphComparator implements Comparator<Pharmacy> {

    public int compare(Pharmacy l, Pharmacy r) {
        return l.getName().compareTo(r.getName());
    }
}
