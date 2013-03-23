package mobi.pharmaapp.view;

import mobi.pharmaapp.util.Location;
import mobi.pharmaapp.util.PharmacyComparator;
import mobi.pharmaapp.util.Scraper;
import mobi.pharmaapp.util.Pharmacy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import eu.pinnoo.appsforghent2013.R;
import mobi.pharmaapp.models.DataModel;
import mobi.pharmaapp.models.UserModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author see /AUTHORS
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        Scraper.loadData(DataModel.getInstance());
        List<Pharmacy> apo = new ArrayList<Pharmacy>();
        apo.addAll(DataModel.getInstance().getPharmacies().values());
        UserModel.getInstance().setCurrentLocation(new Location((float) 51.1006070515313, (float) 3.76332831384537));
        Collections.sort(apo, new PharmacyComparator());
        Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, "" + apo.size());
        for (int i = 0; i < apo.size(); i++) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, apo.get(i).toString());
        }

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
                Intent i = new Intent(getApplicationContext(), GetWaitActivity.class);
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
    }
}
