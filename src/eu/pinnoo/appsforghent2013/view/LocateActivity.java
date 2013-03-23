package eu.pinnoo.appsforghent2013.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.models.DataModel;
import eu.pinnoo.appsforghent2013.util.Apothecary;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocateActivity extends MapActivity {

    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_layout);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getController().setZoom(12);

        //this will let you to zoom in or out using the controllers
        mapView.setBuiltInZoomControls(true);

        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.loc);

        MapOverlayItem itemizedoverlay = new MapOverlayItem(drawable, this);

        Collection<Apothecary> c = DataModel.getInstance().getApothecarys().values();
        List<Apothecary> l = new ArrayList<Apothecary>();
        l.addAll(c);
        for (int i = 0; i < l.size(); i++) {
            GeoPoint point = new GeoPoint((int) (l.get(i).getLocation().getLat() * 1000000), (int) (l.get(i).getLocation().getLon()* 1000000));
            if(i==0){
                mapView.getController().setCenter(point);
            }
            OverlayItem overlayitem = new OverlayItem(point, l.get(i).getName(), l.get(i).getAddress());
            itemizedoverlay.addOverlay(overlayitem);
        }
        mapOverlays.add(itemizedoverlay);
    }

    public void onStatusChanged(String string, int i, Bundle bundle) {
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
