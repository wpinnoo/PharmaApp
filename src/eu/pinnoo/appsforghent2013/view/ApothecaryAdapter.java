package eu.pinnoo.appsforghent2013.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.util.Apothecary;
import java.util.ArrayList;

/**
 *
 * @author stefaanvermassen
 */
public class ApothecaryAdapter extends ArrayAdapter<Apothecary> {

    private ArrayList<Apothecary> objects;
    private ArrayList<Apothecary> original;

    public ApothecaryAdapter(Context context, int textViewResourceId, ArrayList<Apothecary> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        original = new ArrayList<Apothecary>();
        original.addAll(objects);
    }

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

                //If there's nothing to filter on, return the original data for your list
                if (charSequence == null || charSequence.length() == 0) {
                    results.values = original;
                    results.count = original.size();
                } else {
                    ArrayList<Apothecary> filterResultsData = new ArrayList<Apothecary>();
                    for(Apothecary item : original){
                        if(item.getName().startsWith(charSequence.toString().toUpperCase())){
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
                objects.addAll((ArrayList<Apothecary>)filterResults.values);
                
                notifyDataSetChanged();
            }
        };
    }
}
