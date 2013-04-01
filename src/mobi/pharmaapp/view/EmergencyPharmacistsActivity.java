package mobi.pharmaapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.analytics.tracking.android.EasyTracker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.JSONEmergencyPharmacyScraper;

import mobi.pharmaapp.util.Pharmacy;

/**
 *
 * @author see /AUTHORS
 */
public class EmergencyPharmacistsActivity extends ListActivity {

    private Calendar cal;
    private DateFormat datef;
    private PharmacyAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_layout);
        DataModel.getInstance().setEmergencyPharmacistsContainerIfNull(this);
        cal = new GregorianCalendar();
        datef = new SimpleDateFormat();
        new LoadEmergencyDataDialog(this, false) {
            @Override
            protected void onPostExecute(Integer result) {
                this.dialog.dismiss();
                if (result.intValue() == 1) {
                    this.showErrorDialogAndExit();
                } else {
                    setListContent();
                    ((TextView) findViewById(R.id.date_field)).setText("Apothekers voor: " + new SimpleDateFormat().format(DataModel.getInstance().getLastEmPharmsUpdate()));
                }
            }
        }.execute();
    }

    private void setListContent() {
        adapter = new PharmacyAdapter(this, R.layout.list_item, DataModel.getInstance().getEmergencyPharmacies());
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

    private static void showErrorDialogAndExit(final Activity parent) {
        AlertDialog.Builder alert = new AlertDialog.Builder(parent);
        alert.setTitle("No internet connection available!");
        alert.setMessage("You need an internet connection to refresh the list of emergency pharmacists.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
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
                new LoadEmergencyDataDialog(this, true) {
                    @Override
                    protected void onPostExecute(Integer result) {
                        this.dialog.dismiss();
                        if (result.intValue() == 1) {
                            this.showErrorDialogAndExit();
                        } else {
                            setListContent();
                            ((TextView) findViewById(R.id.date_field)).setText("Apothekers voor: " + new SimpleDateFormat().format(DataModel.getInstance().getLastEmPharmsUpdate()));
                        }
                    }
                }.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
