package eu.pinnoo.appsforghent2013.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.models.DataModel;
import eu.pinnoo.appsforghent2013.util.Apothecary;
import eu.pinnoo.appsforghent2013.util.ApothecaryAlfComparator;
import java.util.ArrayList;
import java.util.Collections;
import eu.pinnoo.appsforghent2013.view.InfoPopup;

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
        final ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener(){

            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                InfoPopup.showPopup((Activity) (SearchActivity.this), (Apothecary) (lv.getItemAtPosition(i)));
            }
            
        });
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
