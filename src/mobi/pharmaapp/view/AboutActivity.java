package mobi.pharmaapp.view;

import android.app.Activity;
import android.os.Bundle;
import com.google.analytics.tracking.android.EasyTracker;
import mobi.pharmaapp.R;

/**
 *
 * @author see /AUTHORS
 */
public class AboutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        
        EasyTracker.getInstance().activityStart(this);
    }
}
