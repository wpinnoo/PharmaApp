package mobi.pharmaapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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
        DataModel.getInstance().setPharmacistsContainerIfNull(this);

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

        getCurrentLocation();
    }

    private void getCurrentLocation(){
        Toast.makeText(getApplicationContext(), getString(R.string.get_cur_loc), Toast.LENGTH_LONG).show();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new UserLocationListener());
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

    private class UserLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            UserModel.getInstance().setCurrentLocation(location.getLatitude(), location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

    }
}
