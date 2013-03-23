package eu.pinnoo.appsforghent2013.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.util.Pharmacy;

/**
 *
 * @author see /AUTHORS
 */
public class InfoPopup {

    public InfoPopup() {
    }

    public static void showPopup(Context c, Pharmacy a) {
        Dialog d = new Dialog(c);
        d.setContentView(R.layout.popup_layout);
        d.setTitle(a.getName());
        ((TextView) d.findViewById(R.id.apo_name)).setText("Name: " + a.getName());
        ((TextView) d.findViewById(R.id.apo_address)).setText("Address: " + a.getAddress());
        ((TextView) d.findViewById(R.id.apo_postcode)).setText("Zipcode: " + a.getZipcode());
        ((TextView) d.findViewById(R.id.apo_gemeente)).setText("Town: " + a.getTown());

        d.show();
    }
}
