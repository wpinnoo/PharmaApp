package eu.pinnoo.appsforghent2013.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import eu.pinnoo.appsforghent2013.R;
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
        Drawable drawable = this.getResources().getDrawable(R.drawable.facebook_logo);
         
        MapOverlayItem itemizedoverlay = new MapOverlayItem(drawable, this);
 
        GeoPoint point = new GeoPoint(46066940, 23570000);
       //this will show you the map at the exact location you want (if you not set this you will see the map somewhere in America)
        mapView.getController().setCenter(point);
        OverlayItem overlayitem = new OverlayItem(point, "Title for dialog", "Alba Iulia City From Romania");
 
        GeoPoint point2 = new GeoPoint(35410000, 139460000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Title for dialog", "Japan");
 
        itemizedoverlay.addOverlay(overlayitem);
        itemizedoverlay.addOverlay(overlayitem2);
        mapOverlays.add(itemizedoverlay);
    }

    public void onStatusChanged(String string, int i, Bundle bundle) {
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}
