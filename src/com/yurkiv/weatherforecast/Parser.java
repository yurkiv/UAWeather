package com.yurkiv.weatherforecast;


import android.os.Environment;
import android.util.Log;

import com.yurkiv.weatherparser.ForecastCity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

public class Parser {
	private DefaultHttpClient client = new DefaultHttpClient();
    private String res;
    private int cityIDKey;



    public ForecastCity getForecastCity(InputStreamReader inputStreamReader) {
//        File file = new File(Environment.getExternalStorageDirectory()
//                + "/your_path/23.xml");
		String xml = open(inputStreamReader);
        ForecastCity f=null;
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();

        try
        {

            f = serializer.read(ForecastCity.class, reader, false);
            if (f.getCurrent()==null){
                return null;
            }

        }   catch (org.simpleframework.xml.core.ValueRequiredException e)        {

            return null;


        }   catch (Exception e)
        {
            System.out.println("ErrorParser: "+e);
        }
        return f;
    }

    public static String openUrl(String urlstr){
        BufferedReader in;
        String news=new String();
        String s;
        StringBuffer s2;
        try {
            URL url = new URL(urlstr);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            s2 = new StringBuffer();
            while ((s = in.readLine()) != null) {
                s2.append(s + "\n") ;
            }
            news=s2.toString();
            in.close();
        } catch(FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex){
            System.out.println(ex);
        }
        return news;
    }

    public static String open(InputStreamReader inputStreamReader){
        BufferedReader in;
        String news=new String();
        String s;
        StringBuffer s2;
        try {
            in = new BufferedReader(inputStreamReader);
            s2 = new StringBuffer();
            while ((s = in.readLine()) != null) {
                s2.append(s + "\n") ;
            }
            news=s2.toString();
            in.close();
        } catch(FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex){
            System.out.println(ex);
        }
        return news;
    }

    public static String openIS(InputStream inputStream){
        BufferedReader in;
        String news=new String();
        String s;
        StringBuffer s2;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream));
            s2 = new StringBuffer();
            while ((s = in.readLine()) != null) {
                s2.append(s + "\n") ;
            }
            news=s2.toString();
            in.close();
        } catch(FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex){
            System.out.println(ex);
        }
        return news;
    }

	 public String retrieve(String url) {

	        HttpGet getRequest = new HttpGet(url);

	        try {

	            HttpResponse getResponse = client.execute(getRequest);
	            final int statusCode = getResponse.getStatusLine().getStatusCode();

	            if (statusCode != HttpStatus.SC_OK) {
	                return null;
	            }

	            HttpEntity getResponseEntity = getResponse.getEntity();

	            if (getResponseEntity != null) {
	                return EntityUtils.toString(getResponseEntity);
	            }

	        }
	        catch (IOException e) {
	            getRequest.abort();
	            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
	        }

	        return null;

	    }
}
