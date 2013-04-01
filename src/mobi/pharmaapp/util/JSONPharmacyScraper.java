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

    public static boolean needsUpdate(Activity parent) {
        long lastUpdate = parent.getSharedPreferences("PREFERENCE", Activity.MODE_PRIVATE).getLong("date_pharm_data", 0);
        return System.currentTimeMillis() - lastUpdate > 7 * 24 * 60 * 60 * 1000;
    }

    public static JSONArray readCache(Activity parent) {
        JSONArray arr = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(new File(parent.getCacheDir(), "") + "JSONcache_pharms.srl")));
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

    public static int loadData(DataModel model, Activity parent) {
        JSONArray arr = null;
        if (needsUpdate(parent) && isNetworkAvailable(parent)) {
            arr = downloadData(parent);
        } else {
            arr = readCache(parent);
        }
        return fetchData(arr, model);
    }

    private static boolean isNetworkAvailable(Activity parent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) parent.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    protected static JSONArray downloadData(Activity parent) {
        InputStream inp = getStream("http://datatank.gent.be/Gezondheid/Apotheken.json");
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
                BufferedWriter out = new BufferedWriter(new FileWriter(new File(parent.getCacheDir(), "") + "JSONcache_pharms.srl"));
                out.write(arr.toString());
                out.close();
                parent.getSharedPreferences("PREFERENCE", parent.MODE_PRIVATE)
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

    protected static int fetchData(JSONArray arr, DataModel model) {
        if (arr == null) {
            return 1;
        }
        DataModel.getInstance().reset();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject obj = arr.getJSONObject(i);
                Pharmacy a = new Pharmacy(Float.parseFloat(obj.getString("lat")), Float.parseFloat(obj.getString("long")), Pharmacy.beautifyName(obj.getString("naam")), obj.getString("adres"), obj.getInt("distance"), obj.getString("id"), obj.getString("fid"), Integer.parseInt(obj.getString("postcode")), obj.getString("gemeente"));
                model.addPharmacy(a);
            } catch (JSONException ex) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                Logger.getLogger(JSONPharmacyScraper.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 0;
    }
}