package eu.pinnoo.appsforghent2013.util;

import eu.pinnoo.appsforghent2013.models.DataModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class Scraper {

    public static void loadData(DataModel model) {
        JSONArray arr = downloadData();
        fetchData(arr, model);
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
        InputStream inp = getStream("http://datatank.gent.be/Gezondheid/Apotheken.json");
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp, "iso-8859-1"), 8);
            StringBuilder builder = new StringBuilder();
            builder.append(reader.readLine()).append("\n");

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            inp.close();
            result = builder.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            System.err.println(e);
        }
        JSONArray arr = null;
        try {
            if (!result.isEmpty() && !result.equals("null\n")) {
                JSONObject obj = new JSONObject(result);
                arr = obj.getJSONArray("Apotheken");
            }
        } catch (JSONException ex) {
            Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return arr;
        }
    }

    protected static void fetchData(JSONArray arr, DataModel model) {
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = null;
            try {
                obj = arr.getJSONObject(i);
                Pharmacy a = new Pharmacy(Float.parseFloat(obj.getString("lat")), Float.parseFloat(obj.getString("long")), obj.getString("naam"), obj.getString("adres"), obj.getInt("distance"), obj.getString("id"), obj.getString("fid"), Integer.parseInt(obj.getString("postcode")), obj.getString("gemeente"));
                model.addPharmacy(a);
            } catch (JSONException ex) {
                Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
