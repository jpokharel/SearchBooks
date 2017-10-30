package com.example.android.searchbooks;

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

/**
 * Created by jiwanpokharel89 on 10/24/2017.
 */

public final class Utils {

    private static final int READ_TIME_OUT = 10000;
    private static final int CONNECT_TIME_OUT = 15000;
    private static final String TITLE = "title";
    private static final String VOLUME_INFO = "volumeInfo";
    private static final String ITEMS = "items";
    private static final String AUTHORS = "authors";
    private static final String PRINT_TYPE = "printType";
    private static final String PUBLISHED_DATE = "publishedDate";

    private Utils() {
    }

    public static ArrayList<BookData> getBookInformation(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse))
            return null;
        ArrayList<BookData> books = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject eachJson, propertiesObject;

            if (jsonObject.has(ITEMS)) { //Shall parse only valid data that has items
                JSONArray features = jsonObject.getJSONArray(ITEMS);

                for (int i = 0; i < features.length(); i++) {
                    eachJson = features.getJSONObject(i);
                    propertiesObject = eachJson.getJSONObject(VOLUME_INFO);
                    String title = propertiesObject.getString(TITLE);
                    String author = "";
                    if (propertiesObject.has(AUTHORS)) {
                        JSONArray authors = propertiesObject.optJSONArray(AUTHORS); //To handle no authors.
                        for (int n = 0; n < authors.length(); n++) {
                            if (author == "")
                                author = authors.getString(n);
                            else
                                author += (", " + authors.getString(n));
                        }
                    }
                    author = author.trim();

                    String publishedDate = propertiesObject.getString(PUBLISHED_DATE);
                    String printType = propertiesObject.getString(PRINT_TYPE);

                    books.add(new BookData(title, author, publishedDate, printType));
                }
            }

        } catch (JSONException e) {
            Log.e("Utils", "Problem parsing the book JSON results", e);
        }
        return books;
    }

    public static List<BookData> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException ioe) {
            Log.e("Utils: ", " IO Exception: ", ioe);
        }
        return getBookInformation(jsonResponse);
    }

    public static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Utils.java", "Error code: " + httpURLConnection.getResponseCode());
            }

        } catch (IOException ioe) {
            Log.e("Utils.java", "IOException: ", ioe);
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String eachLine = bufferedReader.readLine();
            while (eachLine != null) {
                jsonResponse.append(eachLine);
                eachLine = bufferedReader.readLine();
            }
        }
        return jsonResponse.toString();
    }


}
