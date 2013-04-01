package mobi.pharmaapp.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import mobi.pharmaapp.models.DataModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static mobi.pharmaapp.util.JSONPharmacyScraper.downloadData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author see /AUTHORS
 */
public class JSONEmergencyPharmacyScraper {

    private static boolean needsUpdate() {
        long lastUpdate = DataModel.getInstance().getEmergencyPharmaciesContainer().getSharedPreferences("PREFERENCE", Activity.MODE_PRIVATE).getLong("date_em_pharm_data", 0);
        return System.currentTimeMillis() - lastUpdate > 30 * 60 * 1000;
    }

    private static JSONArray readCache() {
        JSONArray arr = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(new File(DataModel.getInstance().getEmergencyPharmaciesContainer().getCacheDir(), "") + "JSONcache_em_pharm.srl")));
            String line, content = "";
            while ((line = br.readLine()) != null) {
                content += line;
            }
            arr = new JSONArray(content);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StreamCorruptedException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return arr;
        }
    }

    public static int loadData() {
        JSONArray arr = null;
        if (needsUpdate() && isNetworkAvailable()) {
            arr = downloadData();
        } else {
            arr = readCache();
        }
        long lastUpdate = DataModel.getInstance().getEmergencyPharmaciesContainer().getSharedPreferences("PREFERENCE", Activity.MODE_PRIVATE).getLong("date_em_pharm_data", 0);
        DataModel.getInstance().setLastEmPharmsUpdate(new Date(lastUpdate));
        return fetchData(arr);
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DataModel.getInstance().getEmergencyPharmaciesContainer().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    protected static InputStream getStream(String full_url) {
        try {
            URL url = new URL(full_url);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000);
            return urlConnection.getInputStream();
        } catch (Exception ex) {
            return null;
        }
    }

    protected static JSONArray downloadData() {
        InputStream inp = getStream("http://data.pharmaapp.mobi/em_pharms.json");
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp, "iso-8859-1"), 8);
            StringBuilder builder = new StringBuilder();
            builder.append(reader.readLine()).append("\n");

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inp.close();
            result = builder.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, e);
        }
        JSONArray arr = null;
        try {
            if (!result.isEmpty() && !result.equals("null\n")) {
                JSONObject obj = new JSONObject(result);
                arr = obj.getJSONArray("Apotheken");
            }
        } catch (JSONException ex) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(new File(DataModel.getInstance().getEmergencyPharmaciesContainer().getCacheDir(), "") + "JSONcache_em_pharm.srl"));
                out.write(arr.toString());
                out.close();
                DataModel.getInstance().getEmergencyPharmaciesContainer().getSharedPreferences("PREFERENCE", DataModel.getInstance().getEmergencyPharmaciesContainer().MODE_PRIVATE)
                        .edit()
                        .putLong("date_em_pharm_data", System.currentTimeMillis())
                        .commit();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                return arr;
            }
        }
    }

    protected static int fetchData(JSONArray arr) {
        if(arr == null){
            return 1;
        }
        DataModel.getInstance().reset();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject obj = arr.getJSONObject(i);
                String address = obj.getString("street") + " " + obj.getString("nr");
                Pharmacy a = new Pharmacy((float) 0, (float) 0, obj.getString("name"), address, 0, "0", "0", Integer.parseInt(obj.getString("zip")), obj.getString("city"), obj.getString("tel"));
                DataModel.getInstance().addEmergencyPharmacy(a);
            } catch (JSONException ex) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 0;
    }
}