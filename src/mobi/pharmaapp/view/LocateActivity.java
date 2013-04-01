package mobi.pharmaapp.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import mobi.pharmaapp.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.util.Pharmacy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author see /AUTHORS
 */
public class LocateActivity extends MapActivity {

    private MapView mapView;
    private GeoPoint curLoc;
    private MapOverlayItem previousLoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_layout);
        DataModel.getInstance().setPharmacistsContainerIfNull(this);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getController().setZoom(14);

        mapView.setBuiltInZoomControls(true);

        final List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.loc);
        final MapOverlayItem itemizedoverlay = new MapOverlayItem(drawable, this);

        new LoadDataDialog(this) {
            @Override
            protected void onPostExecute(Integer result) {
                this.dialog.dismiss();
                if (result.intValue() == 1) {
                    this.showErrorDialogAndExit();
                } else {
                    addOverlays(itemizedoverlay, mapOverlays);
                }
            }
        }.execute();

        Toast.makeText(getApplicationContext(), "Getting your current location...", Toast.LENGTH_LONG).show();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MapLocationListener ll = new MapLocationListener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    }

    private void addOverlays(MapOverlayItem itemizedoverlay, List<Overlay> mapOverlays) {
        Collection<Pharmacy> c = DataModel.getInstance().getPharmacies().values();
        List<Pharmacy> l = new ArrayList<Pharmacy>();
        l.addAll(c);
        for (int i = 0; i < l.size(); i++) {
            GeoPoint point = new GeoPoint((int) (l.get(i).getLocation().getLat() * 1000000), (int) (l.get(i).getLocation().getLon() * 1000000));
            if (i == 0) {
                mapView.getController().setCenter(point);
            }
            OverlayItem overlayitem = new OverlayItem(point, l.get(i).getName(), l.get(i).getAddress());
            itemizedoverlay.addOverlay(overlayitem);
        }
        mapOverlays.add(itemizedoverlay);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
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

    private class MapLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            if (location != null) {
                List<Overlay> mapOverlays = mapView.getOverlays();
                Drawable drawable = LocateActivity.this.getResources().getDrawable(R.drawable.curloc);
                MapOverlayItem itemizedoverlay = new MapOverlayItem(drawable, LocateActivity.this);

                curLoc = new GeoPoint(
                        (int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));

                mapView.getController().animateTo(curLoc);
                mapView.getController().setZoom(15);

                OverlayItem overlayitem = new OverlayItem(curLoc, "Me", "My current location");
                itemizedoverlay.addOverlay(overlayitem);
                if(previousLoc != null) {
                    mapOverlays.remove(previousLoc);
                }
                mapOverlays.add(itemizedoverlay);
                previousLoc = itemizedoverlay;
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }
}
