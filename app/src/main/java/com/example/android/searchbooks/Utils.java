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

    private Utils(){}

    public static ArrayList<BookData> getBookInformation(String jsonResponse){
        if(TextUtils.isEmpty(jsonResponse))
            return null;
        ArrayList<BookData> books = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject eachJson,propertiesObject;
            JSONArray features = jsonObject.getJSONArray("items");

            for (int i=0;i<features.length();i++) {
                eachJson = features.getJSONObject(i);
                propertiesObject = eachJson.getJSONObject("volumeInfo");
                String title = propertiesObject.getString("title");
                String author = propertiesObject.getString("authors");
                int publishedDate = propertiesObject.getInt("publishedDate");
                String printType = propertiesObject.getString("printType");


                books.add(new BookData(title,author,publishedDate,printType));
            }

        }catch (JSONException e){
            Log.e("Utils", "Problem parsing the book JSON results", e);
        }
        return books;
    }

    public static List<BookData> fetchBookData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException ioe){
            Log.e("Query Utils: ", " IO Exception at line 90", ioe);
        }
        return getBookInformation(jsonResponse);
    }

    public static URL createUrl(String requestUrl){
        URL url = null;
        try{
            url= new URL(requestUrl);
        }catch(MalformedURLException mue){
            mue.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws  IOException{
        String jsonResponse="";

        if (url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse= readFromStream(inputStream);
            }else{
                Log.e("Utils.java","Error code: "+httpURLConnection.getResponseCode());
            }

        }catch(IOException ioe){
            Log.e("Utils.java", "IOException at line 118", ioe);
        }finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder jsonResponse=new StringBuilder();
        if(inputStream !=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String eachLine = bufferedReader.readLine();
            while (eachLine != null){
                jsonResponse.append(eachLine);
                eachLine=bufferedReader.readLine();
            }
        }
        return jsonResponse.toString();
    }

}
