package eu.pinnoo.appsforghent2013.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.models.DataModel;
import eu.pinnoo.appsforghent2013.util.Apothecary;
import eu.pinnoo.appsforghent2013.util.ApothecaryAlfComparator;
import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends ListActivity {

    private EditText filterText = null;
   // private ArrayAdapter<String> adapter = null;
    private ArrayList<Apothecary> list = null;
    private ApothecaryAdapter adapter = null;

    /**
     * Called when the activity is first created.
     */
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_layout);
        list = new ArrayList<Apothecary>();
        list.addAll(DataModel.getInstance().getApothecarys().values());
        Collections.sort(list, new ApothecaryAlfComparator());
        adapter = new ApothecaryAdapter(this, R.layout.list_item, list);
        setListAdapter(adapter);
        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
    }
    
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            adapter.getFilter().filter(s);
        }
    };
}
