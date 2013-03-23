/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.pinnoo.appsforghent2013.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.R;

/**
 *
 * @author flash
 */
public class InfoPopup {
    
    public InfoPopup(){}
    
    public static void showPopup(Context c){
        Dialog d = new Dialog(c); 
        d.setContentView(R.layout.popup_layout);
        //TextView txt = (TextView)d.findViewById(R.id.textbox);
        //txt.setText("Jooo");
        d.show();
    }
    
}
