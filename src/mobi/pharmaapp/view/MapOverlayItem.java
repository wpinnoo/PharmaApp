package mobi.pharmaapp.view;

/**
 *
 * @author see /AUTHORS
 */
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import java.util.ArrayList;

public class MapOverlayItem extends ItemizedOverlay<OverlayItem> {

    private ArrayList< OverlayItem> mOverlays = new ArrayList< OverlayItem>();
    Context mContext;

    public MapOverlayItem(Drawable marker) {
        super(boundCenterBottom(marker));
    }

    public MapOverlayItem(Drawable marker, Context context) {
        super(boundCenterBottom(marker));
        mContext = context;
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlays.get(i);
    }

    @Override
    public int size() {
        return mOverlays.size();
    }

    @Override
    protected boolean onTap(int i) {
        OverlayItem item = mOverlays.get(i);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();
        return true;
    }
}
