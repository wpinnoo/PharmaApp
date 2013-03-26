package mobi.pharmaapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.HTMLScraper;
import mobi.pharmaapp.util.JSONScraper;

/**
 *
 * @author see /AUTHORS
 */
public class GetWaitActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_layout);

        // Ask the user for this data (zipcode, city, date, etc.)
        new LoadData(DataModel.getInstance(), this, "9000", "Gent", "25", "03", "2013", "2230", "0", "0").execute();
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
        alert.setMessage("You need an internet connection to load the emergency pharmacists.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                parent.finish();
            }
        });
        alert.show();
    }

    private class LoadData extends AsyncTask<Void, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(GetWaitActivity.this);
        private DataModel model;
        private Activity parent;
        private String zipcode, town, day, month, year, hour, lat, lng;

        public LoadData(DataModel model, Activity parent, String zipcode, String town, String day, String month, String year, String hour, String lat, String lng) {
            this.model = model;
            this.parent = parent;
            this.zipcode = zipcode;
            this.town = town;
            this.day = day;
            this.month = month;
            this.year = year;
            this.hour = hour;
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading data...");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return HTMLScraper.loadData(model, parent, zipcode, town, day, month, year, hour, lat, lng);
        }

        @Override
        protected void onPostExecute(Integer result) {
            dialog.dismiss();
            if (result.intValue() == 1) {
                showErrorDialogAndExit(GetWaitActivity.this);
            }
        }
    }
}
