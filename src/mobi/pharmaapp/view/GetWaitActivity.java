package mobi.pharmaapp.view;

import android.app.Activity;
import android.os.Bundle;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;

/**
 *
 * @author see /AUTHORS
 */
public class GetWaitActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_layout);
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
