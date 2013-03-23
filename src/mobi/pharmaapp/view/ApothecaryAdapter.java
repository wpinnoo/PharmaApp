package mobi.pharmaapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.R;
import mobi.pharmaapp.util.Pharmacy;
import java.util.ArrayList;

/**
 *
 * @author see /AUTHORS
 */
public class ApothecaryAdapter extends ArrayAdapter<Pharmacy> {

    private ArrayList<Pharmacy> objects;
    private ArrayList<Pharmacy> original;

    public ApothecaryAdapter(Context context, int textViewResourceId, ArrayList<Pharmacy> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        original = new ArrayList<Pharmacy>();
        original.addAll(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        TextView tt = (TextView) v.findViewById(R.id.toptext);
        try {
            tt.setText(objects.get(position).getName());
        } catch (NullPointerException e) {
            tt.setText("Loading");
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    results.values = original;
                    results.count = original.size();
                } else {
                    ArrayList<Pharmacy> filterResultsData = new ArrayList<Pharmacy>();
                    for (Pharmacy item : original) {
                        if (item.getName().startsWith(charSequence.toString().toUpperCase())) {
                            filterResultsData.add(item);
                        }
                    }
                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                objects.clear();
                objects.addAll((ArrayList<Pharmacy>) filterResults.values);

                notifyDataSetChanged();
            }
        };
    }
}
