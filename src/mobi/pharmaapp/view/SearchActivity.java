package mobi.pharmaapp.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.Pharmacy;
import mobi.pharmaapp.util.PharmacyAlphComparator;
import java.util.ArrayList;
import java.util.Collections;
import mobi.pharmaapp.util.JSONPharmacyScraper;

/**
 *
 * @author see /AUTHORS
 */
public class SearchActivity extends ListActivity {

    private EditText filterText = null;
    private ArrayList<Pharmacy> list = null;
    private PharmacyAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        DataModel.getInstance().setPharmacistsContainerIfNull(this);
        new LoadDataDialog(this) {
                    @Override
                    protected void onPostExecute(Integer result) {
                        this.dialog.dismiss();
                        if (result.intValue() == 1) {
                            this.showErrorDialogAndExit();
                        } else {
                            fillList();
                        }
                    }
                }.execute();

        filterText = (EditText) findViewById(R.id.search_box);
        filterText.addTextChangedListener(filterTextWatcher);
        final ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                InfoPopup.showPopup(SearchActivity.this, (Pharmacy) (lv.getItemAtPosition(i)));
            }
        });
    }

    public void fillList() {
        list = new ArrayList<Pharmacy>();
        list.addAll(DataModel.getInstance().getPharmacies().values());
        Collections.sort(list, new PharmacyAlphComparator());
        adapter = new PharmacyAdapter(this, R.layout.list_item, list);
        setListAdapter(adapter);
    }
    private final TextWatcher filterTextWatcher = new TextWatcher() {
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

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.refresh:
                new LoadDataDialog(this) {
                    @Override
                    protected void onPostExecute(Integer result) {
                        this.dialog.dismiss();
                        if (result.intValue() == 1) {
                            this.showErrorDialogAndExit();
                        } else {
                            fillList();
                        }
                    }
                }.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
