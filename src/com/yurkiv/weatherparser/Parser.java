package com.yurkiv.weatherparser;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Parser {


    public static void main(String[] args) {
        System.out.println(getForecastCity().toString());
    }

	public static ForecastCity getForecastCity() {

		String xml = retrieve("http://xml.weather.co.ua/1.2/forecast/23?dayf=5&lang=uk");
        ForecastCity f=null;
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();

        try
        {

            f = serializer.read(ForecastCity.class, reader, false);
            

        }
        catch (Exception e)
        {
            System.out.println("Error");
        }
        return f;
    }

	 public static String retrieve(String url) {
            DefaultHttpClient client = new DefaultHttpClient();
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
	            //Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
	        }

	        return null;

	    }
}
