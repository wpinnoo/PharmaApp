package mobi.pharmaapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.JSONEmergencyPharmacyScraper;
import mobi.pharmaapp.util.JSONPharmacyScraper;

/**
 *
 * @author see /AUTHORS
 */
public class LoadEmergencyDataDialog extends AsyncTask<Void, Void, Integer> {

    private final Activity parent;
    protected ProgressDialog dialog;
    private boolean force;

    public LoadEmergencyDataDialog(Activity parent, boolean force) {
        this.parent = parent;
        dialog = new ProgressDialog(parent);
        this.force = force;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading data...");
        dialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return JSONEmergencyPharmacyScraper.loadData(force);
    }

    @Override
    protected void onPostExecute(Integer result) {
        dialog.dismiss();
        if (result.intValue() == 1) {
            showErrorDialogAndExit();
        }
    }

    protected void showErrorDialogAndExit() {
        AlertDialog.Builder alert = new AlertDialog.Builder(parent);
        alert.setTitle("No internet connection available!");
        alert.setMessage("You need an internet connection to refresh the list of emergency pharmacists.");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}