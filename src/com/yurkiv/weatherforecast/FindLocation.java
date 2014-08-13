package com.yurkiv.weatherforecast;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.simpleframework.xml.core.Persister;
import org.xml.sax.InputSource;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.yurkiv.weatherparser.CityAll;

public class FindLocation extends AsyncTask<String, Void, Exception>{
	private ProgressDialog progressDialog;
	private Context context;
	private Notifier notifier;
	private Exception exception;
	private LocationManager locationManager;
	private String provider;
	private int cityID=0;
	private String cityName="";
	
	
	public FindLocation(Context context, Notifier notifier) {
        if (context == null) throw new IllegalArgumentException("parentActivity");           
        this.context = context;  
        this.notifier=notifier;
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(false);
    }		
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        this.progressDialog.setMessage(context.getResources().getString(R.string.search_location));
        this.progressDialog.show();
    }					
	@Override
	protected Exception doInBackground(String... params) {
		try {
			
			findLocation();
		} catch (Exception e) {				
			exception=e;
		}
		return null;
	}
	
	private void findLocation() throws Exception{
		String zipcode=getZipCode();
		int code=Integer.parseInt(zipcode.substring(0, 2));
		
		Log.d("GPS", "code "+Integer.toString(code));					
		
		if(code>=95 && code<=98){
			cityID=22;
		} else if (code>=21 && code<=24){
			cityID=10;
		} else if (code>=43 && code<=45){
			cityID=47;
		} else if (code>=49 && code<=53){
			cityID=16;
		} else if (code>=83 && code<=87){
			cityID=7;
		} else if (code>=10 && code<=13){
			cityID=3;
		} else if (code>=88 && code<=90){
			cityID=30;
		} else if (code>=69 && code<=72){
			cityID=17;
		} else if (code>=76 && code<=78){
			cityID=32;
		} else if (code>=1 && code<=9){
			cityID=23;
		} else if (code>=25 && code<=28){
			cityID=14;
		} else if (code>=91 && code<=94){
			cityID=31;
		} else if (code>=79 && code<=82){
			cityID=15;
		} else if (code>=54 && code<=57){
			cityID=11;
		} else if (code>=65 && code<=68){
			cityID=25;
		} else if (code>=36 && code<=39){
			cityID=1;
		} else if (code>=33 && code<=35){
			cityID=6;
		} else if (code>=40 && code<=42){
			cityID=4;
		} else if (code>=46 && code<=48){
			cityID=13;
		} else if (code>=61 && code<=64){
			cityID=19;
		} else if (code>=73 && code<=75){
			cityID=7;
		} else if (code>=29 && code<=32){
			cityID=12;
		} else if (code>=18 && code<=20){
			cityID=2;
		} else if (code>=58 && code<=60){
			cityID=9;
		} else if (code>=14 && code<=17){
			cityID=5;
		}		
		
		String locale = Locale.getDefault().getLanguage();
		InputStream is = context.getResources().openRawResource(R.raw.city_en);
		Log.d("Locale", "Locale: " + locale);
		if (locale.equals("ru")) {
			Log.d("Locale", "Locale: " + locale);
			is = context.getResources().openRawResource(R.raw.city_ru);
		} else if (locale.equals("uk")) {
			Log.d("Locale", "Locale: " + locale);
			is = context.getResources().openRawResource(R.raw.city_uk);
		}
		String xml = Parser.openIS(is);
		Reader reader = new StringReader(xml);
		Persister serializer = new Persister();
		CityAll cityAll = serializer.read(CityAll.class, reader, false);
		final List<CityAll.City> cityList = cityAll.getCities();
		final HashMap<Integer, String> idCityName = new HashMap<Integer, String>();			
		for (int i = 0; i < cityList.size(); i++) {
			idCityName.put(cityList.get(i).getId(), cityList.get(i).getName());
		}
		cityName=idCityName.get(cityID);		       
		
	}
	
	private String getZipCode() {
		String zipcode="";
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);			
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    if (location != null) {
	        try {
				System.out.println("Provider " + provider + " has been selected.");
				Log.d("GPS", "Start");
		        double lat = location.getLatitude();
			    double lng = location.getLongitude();
		        
			    XPath xpath = XPathFactory.newInstance().newXPath();
		    	String expression = "//GeocodeResponse/result/address_component[type=\"postal_code\"]/long_name/text()";
		    	InputSource inputSource = new InputSource("https://maps.googleapis.com/maps/api/geocode/xml?latlng="+lat+","+lng+"&sensor=true");
		    	
				zipcode = (String) xpath.evaluate(expression, inputSource, XPathConstants.STRING);
									
			    Log.d("GPS", "zip code:" + zipcode);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	
	    				    
	    } else {
	    	cityID=0;
	    }
	    return zipcode;
			    			
	}		
			
	@Override
	protected void onPostExecute(Exception result) {
		super.onPostExecute(result);
		if (result != null) {
			this.progressDialog.dismiss();
			Log.d("DownloadManager", "Error: " + result);
			Toast.makeText(context, context.getResources().getString(R.string.download_error),Toast.LENGTH_LONG).show();
         } else {
        	 if (this.progressDialog.isShowing()) {
                 this.progressDialog.dismiss();
             } 
             
             if (notifier != null) {
                 notifier.operationFinished(context, cityID, cityName);
             }
         }	
		 
	}		
}    