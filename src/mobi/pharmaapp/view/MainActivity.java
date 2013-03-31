package mobi.pharmaapp.view;

import mobi.pharmaapp.util.Location;
import mobi.pharmaapp.util.JSONPharmacyScraper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.models.UserModel;

/**
 *
 * @author see /AUTHORS
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        new LoadData().execute();

        UserModel.getInstance().setCurrentLocation(new Location((float) 51.1006070515313, (float) 3.76332831384537));

        Button btn_search = (Button) findViewById(R.id.btn_search);
        Button btn_nearby = (Button) findViewById(R.id.btn_nearby);
        Button btn_emergency = (Button) findViewById(R.id.btn_emergency);
        Button btn_about = (Button) findViewById(R.id.btn_about);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        btn_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LocateActivity.class);
                startActivity(i);
            }
        });

        btn_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmergencyPharmacistsActivity.class);
                startActivity(i);
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
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
        alert.setMessage("You need an internet connection the first time you run this app. The app will close now.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                parent.finish();
            }
        });
        alert.show();
    }

    private class LoadData extends AsyncTask<Void, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading data...");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return JSONPharmacyScraper.loadData(DataModel.getInstance(), MainActivity.this);
        }

        @Override
        protected void onPostExecute(Integer result) {
            dialog.dismiss();
            if (result.intValue() == 1) {
                showErrorDialogAndExit(MainActivity.this);
            }
        }
    }
}
