package moka.net.a6;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<String> fetchEarthquakeData(String requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
            return null;
        }
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        InputStream inputStream = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Error response code: ");
                sb.append(urlConnection.getResponseCode());
                Log.e(LOG_TAG, sb.toString());
            }
            urlConnection.disconnect();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        } catch (Throwable th) {

            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                output.append(line);
            }
        }
        return output.toString();
    }

    private static List<String> extractFeatureFromJson(String earthquakeJSON) {
        String str = "@@";
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        List<String> dataList = new ArrayList<>();
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
                int i = 0;
                while (i < earthquakeArray.length()) {
                    StringBuilder sb = new StringBuilder();
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");

                    JSONArray coord = currentEarthquake.getJSONObject("geometry").getJSONArray("coordinates");

                    String lat = coord.getString(0);
                    String lng = coord.getString(1);

                    sb.append(properties.getString("title"));
                    sb.append(str);
                    sb.append(properties.getString("time"));
                    sb.append(str);
                    sb.append(properties.getString("url"));
                    sb.append(str);
                    sb.append(lat);
                    sb.append(str);
                    sb.append(lng);
                    sb.append(str);
                    sb.append(properties.getString("mag"));

                    dataList.add(sb.toString());
                    i++;
                }
                return dataList;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
                return null;
            }
    }
}
