package eu.pinnoo.appsforghent2013.view;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import eu.pinnoo.appsforghent2013.R;


public class LocateActivity extends MapActivity {

    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);
        mapView = (MapView) findViewById(R.id.mapView);
    }

    public void onStatusChanged(String string, int i, Bundle bundle) {
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}
