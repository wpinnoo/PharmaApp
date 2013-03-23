package eu.pinnoo.appsforghent2013.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.util.Apothecary;
import java.util.ArrayList;

/**
 *
 * @author stefaanvermassen
 */
public class ApothecaryAdapter extends ArrayAdapter<Apothecary> {

    private ArrayList<Apothecary> objects;
    private Activity activity;

    public ApothecaryAdapter(Activity activity, int textViewResourceId, ArrayList<Apothecary> objects) {
        super(activity, textViewResourceId, objects);
        this.objects = objects;
        this.activity = activity;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TextView v = (TextView) super.getView(position, convertView, parent);
        if (v == null) {
            v = new TextView(activity);
        }
        try {
            v.setText(objects.get(position).getName());
        } catch (NullPointerException e) {
            v.setText("Loading");
        }
        return v;

    }
}
