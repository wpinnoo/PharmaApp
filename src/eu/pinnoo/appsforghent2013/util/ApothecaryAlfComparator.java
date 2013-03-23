/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.util;

import java.util.Comparator;

/**
 *
 * @author stefaanvermassen
 */
public class ApothecaryAlfComparator implements Comparator<Apothecary>{

    public int compare(Apothecary l, Apothecary r) {
       return l.getName().compareTo(r.getName());
    }
    
}
