package com.example.assign6oopen;

import android.text.TextUtils;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {
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
        String jsonResponse = BuildConfig.FLAVOR;
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            HttpURLConnection urlConnection2 = (HttpURLConnection) url.openConnection();
            urlConnection2.setReadTimeout(10000);
            urlConnection2.setConnectTimeout(15000);
            urlConnection2.setRequestMethod("GET");
            urlConnection2.connect();
            if (urlConnection2.getResponseCode() == 200) {
                inputStream = urlConnection2.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Error response code: ");
                sb.append(urlConnection2.getResponseCode());
                Log.e(str, sb.toString());
            }
            if (urlConnection2 != null) {
                urlConnection2.disconnect();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
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
        List<String> quakeList = new ArrayList<>();
        try {
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
                int i = 0;
                int i2 = 0;
                while (i2 < earthquakeArray.length()) {
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i2);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");
                    String quakeTitle = properties.getString("title");
                    String quakeMag = properties.getString("mag");
                    String quakeTime = properties.getString("time");
                    String quakeURL = properties.getString("url");
                    JSONArray coord = currentEarthquake.getJSONObject("geometry").getJSONArray("coordinates");
                    String lat = coord.getString(i);
                    String lng = coord.getString(1);
                    StringBuilder sb = new StringBuilder();
                    sb.append(quakeTitle);
                    sb.append(str);
                    sb.append(quakeTime);
                    sb.append(str);
                    sb.append(quakeURL);
                    sb.append(str);
                    JSONObject baseJsonResponse2 = baseJsonResponse;
                    sb.append(lat);
                    sb.append(str);
                    sb.append(lng);
                    sb.append(str);
                    sb.append(quakeMag);
                    quakeList.add(sb.toString());
                    i2++;
                    baseJsonResponse = baseJsonResponse2;
                    i = 0;
                }
                return quakeList;
            } catch (JSONException e) {
                e = e;
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
                return null;
            }
        } catch (JSONException e2) {
            e = e2;
            String str2 = earthquakeJSON;
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            return null;
        }
    }
}
