package mobi.pharmaapp.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import java.text.SimpleDateFormat;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;

import mobi.pharmaapp.util.Pharmacy;

/**
 *
 * @author see /AUTHORS
 */
public class EmergencyPharmacistsActivity extends ListActivity {

    private PharmacyAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_layout);
        DataModel.getInstance().setEmergencyPharmacistsContainerIfNull(this);
        new LoadEmergencyDataDialog(this) {
            @Override
            protected void onPostExecute(Integer result) {
                this.dialog.dismiss();
                if (result.intValue() == 1) {
                    this.showErrorDialogAndExit();
                } else {
                    setListContent();
                    ((TextView) findViewById(R.id.date_field)).setText(getString(R.string.em_pharms_date_prefix) + new SimpleDateFormat().format(DataModel.getInstance().getLastEmPharmsUpdate()));
                }
            }
        }.execute();
    }

    private void setListContent() {
        adapter = new PharmacyAdapter(this, R.layout.list_item, DataModel.getInstance().getEmergencyPharmacies(DataModel.LIST_TYPE.SORT_ON_DISTANCE));
        setListAdapter(adapter);
        final ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                InfoPopup.showPopup(EmergencyPharmacistsActivity.this, (Pharmacy) (lv.getItemAtPosition(i)));
            }
        });
    }

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
        inflater.inflate(R.menu.em_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                new LoadEmergencyDataDialog(this, true) {
                    @Override
                    protected void onPostExecute(Integer result) {
                        this.dialog.dismiss();
                        if (result.intValue() == 1) {
                            this.showErrorDialogAndExit();
                        } else {
                            setListContent();
                            ((TextView) findViewById(R.id.date_field)).setText(getString(R.string.em_pharms_date_prefix) + new SimpleDateFormat().format(DataModel.getInstance().getLastEmPharmsUpdate()));
                        }
                    }
                }.execute();
                return true;
            case R.id.show_on_map:
                DataModel.getInstance().setMapMode(DataModel.MAP_MODE.EMERGENCY_ONLY);
                Intent i = new Intent(getApplicationContext(), LocateActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
