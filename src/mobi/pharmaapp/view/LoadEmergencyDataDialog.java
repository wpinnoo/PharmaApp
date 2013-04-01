package mobi.pharmaapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import mobi.pharmaapp.R;
import mobi.pharmaapp.util.JSONEmergencyPharmacyScraper;

/**
 *
 * @author see /AUTHORS
 */
public class LoadEmergencyDataDialog extends AsyncTask<Void, Void, Integer> {

    private final Activity parent;
    protected ProgressDialog dialog;
    private boolean force;

    public LoadEmergencyDataDialog(Activity parent){
        this(parent, false);
    }
    
    public LoadEmergencyDataDialog(Activity parent, boolean force) {
        this.parent = parent;
        dialog = new ProgressDialog(parent);
        this.force = force;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage(parent.getString(R.string.loading_data));
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
        alert.setTitle(parent.getString(R.string.internet_conn_error_title));
        alert.setMessage(parent.getString(R.string.internet_conn_error_msg_em_pharmlist));
        alert.setPositiveButton(parent.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}