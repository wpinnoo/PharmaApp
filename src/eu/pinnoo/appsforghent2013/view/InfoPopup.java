/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.util.Apothecary;

/**
 *
 * @author flash
 */
public class InfoPopup {
    
    public InfoPopup(){}
    
    public static void showPopup(Context c, Apothecary a){
        Dialog d = new Dialog(c); 
        d.setContentView(R.layout.popup_layout);
        d.setTitle(a.getName());
        ((TextView)d.findViewById(R.id.apo_name)).setText("Naam: " + a.getName());
        ((TextView)d.findViewById(R.id.apo_address)).setText("Adres: " + a.getAddress());
        ((TextView)d.findViewById(R.id.apo_postcode)).setText("Postcode: " + a.getPostcode());
        ((TextView)d.findViewById(R.id.apo_gemeente)).setText("Gemeente: " + a.getGemeente());
        
        
        d.show();
    }
    
}
