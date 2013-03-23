package mobi.pharmaapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import eu.pinnoo.appsforghent2013.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_layout);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getController().setZoom(12);

        mapView.setBuiltInZoomControls(true);

        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.loc);

        MapOverlayItem itemizedoverlay = new MapOverlayItem(drawable, this);

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
}
