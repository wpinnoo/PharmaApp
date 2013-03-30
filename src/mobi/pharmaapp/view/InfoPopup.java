package mobi.pharmaapp.view;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.regex.Pattern;
import mobi.pharmaapp.R;
import mobi.pharmaapp.util.Pharmacy;

/**
 *
 * @author see /AUTHORS
 */
public class InfoPopup {

    public InfoPopup() {
    }

    public static void showPopup(final Context c, final Pharmacy a) {
        Dialog d = new Dialog(c);
        d.setContentView(R.layout.popup_layout);
        d.setTitle(a.getName());
        ((TextView) d.findViewById(R.id.apo_name)).setText("Name: " + a.getName());
        ((TextView) d.findViewById(R.id.apo_address)).setText("Address: " + a.getAddress());
        ((TextView) d.findViewById(R.id.apo_zipcode)).setText("Zipcode: " + a.getZipcode());
        ((TextView) d.findViewById(R.id.apo_town)).setText("Town: " + a.getTown());
        a.setTelnr("+3293445433");
        if (!"".equals(a.getTelnr())) {
            LinearLayout ll = (LinearLayout) d.findViewById(R.id.layout);
            TextView tel = new TextView(c);
            tel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tel.setText("Telephone: " + a.getTelnr());
            ll.addView(tel);
            Linkify.addLinks(tel, Pattern.compile("\\+?(\\d){8,11}"), "tel:");
        }
        d.show();
    }
}
