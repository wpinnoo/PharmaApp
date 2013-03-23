/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.models;

import eu.pinnoo.appsforghent2013.util.Apothecary;
import java.util.HashMap;

/**
 *
 * @author flash
 */
public class DataModel {
    
    private HashMap<String, Apothecary> apothecarys;
    
    public DataModel(){
        apothecarys = new HashMap<String, Apothecary>();
    }
    
    public HashMap<String, Apothecary> getApothecarys(){
        return apothecarys;
    }
    
    /*
     * Can return null
     */
    public Apothecary getApothecary(String id){
        return apothecarys.get(id);
    }
    
    public void addApothecary(String id, Apothecary a){
        apothecarys.put(id, a);
    }    
}
