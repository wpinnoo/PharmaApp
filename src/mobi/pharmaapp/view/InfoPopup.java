package mobi.pharmaapp.view;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import mobi.pharmaapp.R;
import mobi.pharmaapp.util.Pharmacy;

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
        ((TextView) d.findViewById(R.id.apo_zipcode)).setText("Zipcode: " + a.getZipcode());
        ((TextView) d.findViewById(R.id.apo_town)).setText("Town: " + a.getTown());
        if (!"".equals(a.getTelnr())){
            LinearLayout ll = (LinearLayout) d.findViewById(R.id.layout);
            TextView tel = new TextView(c);
            tel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            tel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tel.setText("Telephone: " + a.getTelnr());
            ll.addView(tel);
        }
        d.show();
    }
}
