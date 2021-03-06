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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author see /AUTHORS
 */
public class JSONPharmacyScraper {

    private static boolean needsUpdate() {
        long lastUpdate = DataModel.getInstance().getPharmaciesContainer().getSharedPreferences("PREFERENCE", Activity.MODE_PRIVATE).getLong("date_pharm_data", 0);
        return System.currentTimeMillis() - lastUpdate > 7 * 24 * 60 * 60 * 1000;
    }

    private static JSONArray readCache() {
        JSONArray arr = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(new File(DataModel.getInstance().getPharmaciesContainer().getCacheDir(), "") + "JSONcache_pharms.srl")));
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

    public static int loadData(boolean force) {
        JSONArray arr;
        if (force || needsUpdate() && isNetworkAvailable()) {
            arr = downloadData();
        } else {
            arr = readCache();
        }
        return fetchData(arr);
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DataModel.getInstance().getPharmaciesContainer().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    protected static InputStream getStream(String full_url) throws IOException {
        URL url = new URL(full_url);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setConnectTimeout(1000);
        return urlConnection.getInputStream();
    }

    protected static JSONArray downloadData() {
        String result;
        try {
            InputStream inp = getStream(LocalConstants.PHARMACY_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp, LocalConstants.ENCODING), 8);
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
            return null;
        } catch (IOException e) {
            Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, e);
            return null;
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
                BufferedWriter out = new BufferedWriter(new FileWriter(new File(DataModel.getInstance().getPharmaciesContainer().getCacheDir(), "") + "JSONcache_pharms.srl"));
                out.write(arr.toString());
                out.close();
                DataModel.getInstance().getPharmaciesContainer().getSharedPreferences("PREFERENCE", Activity.MODE_PRIVATE)
                        .edit()
                        .putLong("date_pharm_data", System.currentTimeMillis())
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
        if (arr == null) {
            return 1;
        }
        DataModel.getInstance().resetPharmacists();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject obj = arr.getJSONObject(i);
                Pharmacy a = new Pharmacy(Float.parseFloat(obj.getString("lat")), Float.parseFloat(obj.getString("long")), Pharmacy.beautifyName(obj.getString("naam")), obj.getString("adres"), obj.getInt("distance"), obj.getString("id"), obj.getString("fid"), Integer.parseInt(obj.getString("postcode")), obj.getString("gemeente"));
                DataModel.getInstance().addPharmacy(a);
            } catch (JSONException ex) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 0;
    }
}