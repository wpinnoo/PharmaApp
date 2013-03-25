package mobi.pharmaapp.view;

import android.app.Activity;
import android.os.Bundle;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.HTMLScraper;

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
        HTMLScraper.loadData(DataModel.getInstance(), this, "9000", "Gent", "25", "03", "2013", "2230", "0", "0");
    }
    
    @Override
    public void onStart(){
        super.onStart();
        EasyTracker.getInstance().activityStart(this);
    }
    
    @Override
    public void onStop(){
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }
}
