package eu.pinnoo.appsforghent2013.models;

import eu.pinnoo.appsforghent2013.util.Apothecary;
import java.util.HashMap;

/**
 *
 * @author flash
 */
public class DataModel {
    
    private HashMap<String, Apothecary> apothecarys;
    private static final DataModel model = new DataModel();
    
    private DataModel(){
        apothecarys = new HashMap<String, Apothecary>();
    }
    
    public static DataModel getInstance(){
        return model;
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
    
    public void addApothecary(Apothecary a){
        apothecarys.put(a.getId(), a);
    }    
}
