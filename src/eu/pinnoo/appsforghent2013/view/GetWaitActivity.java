package eu.pinnoo.appsforghent2013.view;

import android.app.Activity;
import android.os.Bundle;
import eu.pinnoo.appsforghent2013.R;
import eu.pinnoo.appsforghent2013.models.DataModel;

public class GetWaitActivity extends Activity {
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_layout);
        InfoPopup.showPopup(this, DataModel.getInstance().getApothecarys().get("kml_1"));
    }
}
