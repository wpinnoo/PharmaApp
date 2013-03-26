package mobi.pharmaapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import mobi.pharmaapp.models.DataModel;

/**
 *
 * @author see /AUTHORS
 */
public class HTMLScraper {

    public static int loadData(DataModel model, Activity parent, String zipcode, String town, String day, String month, String year, String hour, String lat, String lng) {
        if (!isNetworkAvailable(parent)) {
            return 1;
        }
        String content = downloadData(parent, zipcode, town, day, month, year, hour, lat, lng);
        fetchData(content, model);
        return 0;
    }

    private static boolean isNetworkAvailable(Activity parent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) parent.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    protected static String encodePostParameters(String[] keys, String[] values) {
        String data = "";
        for (int i = 0; i < keys.length; i++) {
            try {
                data += URLEncoder.encode(keys[i], "UTF-8") + "="
                        + URLEncoder.encode(values[i], "UTF-8");
                if (i < keys.length - 1) {
                    data += "&";
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(HTMLScraper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    protected static String downloadData(Activity parent, String zipcode, String town, String day, String month, String year, String hour, String lat, String lng) {
        String content = "";
        try {
            String[] keys = new String[]{"zip_code", "city", "T_dag", "T_maand", "T_jaar", "T_hour", "lat", "lng", "submit"};
            String[] values = new String[]{zipcode, town, day, month, year, hour, lat, lng, "zoeken apothekers van wacht"};

            URL url = new URL("http://admin.ringring.be/apb/public/duty_geo2.asp?lan=1");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(encodePostParameters(keys, values));
            wr.flush();

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            wr.close();
            rd.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTMLScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTMLScraper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return content;
        }
    }

    protected static void fetchData(String input, DataModel model) {
        String[] content = new String[0];
        for (String s : input.split("\n")) {
            if (s.contains("table id=\"listResults\"")) {
                content = s.split("<tr>");
                break;
            }
        }

        for (String s : content) {
            if (!(s.contains("van wacht") && (s.contains("styleGreenText")) || s.contains("styleRedText")) || s.contains("Bel naar het nummer")) {
                continue;
            }
            String telnr = "";
            String name = "";
            String address = "";
            String town = "";
            String zipcode = "";
            String[] contentLines = s.split("&nbsp;");
            boolean nameFound = false, townFound = false, addressFound = false;
            for (String l : contentLines) {
                if (l.matches(".*<b>[A-Z]{2}.*")) {
                    name = beautifyName(l.replaceAll("<[^>]*>", "").trim());
                    nameFound = true;
                    continue;
                }
                if (nameFound && l.matches(".*[A-Z][^ ]+.* [0-9]+.*") && !l.matches(".*[0-9]:[0-9].*") && !l.matches(".*Tel\\. :.*")) {
                    address = l.replaceAll("<[^>]*>", "").trim();
                    addressFound = true;
                    continue;
                }
                if (addressFound && l.matches(".*[0-9]{4} [A-Z].*")) {
                    town = l.replaceAll("<[^>]*>", "").replaceAll(".*[0-9] ", "").trim();
                    zipcode = l.replaceAll("<[^>]*>", "").replaceAll(" [A-Z].*", "").trim();
                    townFound = true;
                    continue;
                }
                if (townFound && l.matches(".*Tel[.] :</b> [0-9]{9}.*")) {
                    telnr = l.replaceAll("<[^>]*>", "").replaceAll("Tel\\. : ", "").trim();
                }
            }
            Pharmacy p = null;
            try {
                p = new Pharmacy((float) 0, (float) 0, name, address, 0, "0", "0", Integer.parseInt(zipcode), town, telnr);
            } catch (NumberFormatException e) {
                p = new Pharmacy((float) 0, (float) 0, name, address, 0, "0", "0", 0, town, telnr);
            } finally {
                DataModel.getInstance().addEmergencyPharmacy(p);
            }
        }
    }

    private static String beautifyName(String name) {
        String result = "";
        for (String subname : name.split("\n")[0].split(" ")) {
            char letter = subname.charAt(0);
            subname = subname.toLowerCase();
            result += letter + subname.substring(1, subname.length()) + " ";
        }
        return result.trim();
    }
}
