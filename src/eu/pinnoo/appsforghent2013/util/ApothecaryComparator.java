/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.util;

import eu.pinnoo.appsforghent2013.models.UserModel;
import java.util.Comparator;

/**
 *
 * @author flash
 */
public class ApothecaryComparator implements Comparator<Apothecary>{
    
    public int compare(Apothecary l, Apothecary r) {
        float resultl = (float) (Math.sqrt(Math.pow(2, l.getLocation().getLat() - UserModel.getUserModel().getCurrentLocation().getLat())
                + Math.pow(2, l.getLocation().getLon() - UserModel.getUserModel().getCurrentLocation().getLon())));
        float resultr = (float) (Math.sqrt(Math.pow(2, r.getLocation().getLat() - UserModel.getUserModel().getCurrentLocation().getLat())
                + Math.pow(2, r.getLocation().getLon() - UserModel.getUserModel().getCurrentLocation().getLon())));
        return (resultl < resultr) ? -1 : 1;
    }
    
}
