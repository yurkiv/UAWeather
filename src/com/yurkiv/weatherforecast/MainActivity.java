package com.yurkiv.weatherforecast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements TabListener {

	private ActionBar actionBar;
	private int cityID = 23;	
	private static final String MyPREFERENCES = "MyPrefs";
	private static final String cityName = "cityNameKey";
	private static final String cityIDKey = "cityIDKey";
	private SharedPreferences sharedpreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);		
		getOverflowMenu();		
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(cityName)) {			
			cityID = sharedpreferences.getInt("cityIDKey", 0);			
			setupTabs();			
		} else {			
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
		}
	}
	
	private void setupTabs() {
		actionBar = getSupportActionBar();		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		if(actionBar.getTabCount()>0){
			actionBar.removeAllTabs();
		}
		Tab currentTab = actionBar.newTab();
		currentTab.setText(getResources().getString(R.string.current_tab_name));		
		currentTab.setTabListener(this);
		actionBar.addTab(currentTab, 0, true);

		Tab forecastTab = actionBar.newTab();
		forecastTab.setText(getResources().getString(R.string.forecast_tab_name));		
		forecastTab.setTabListener(this);
		actionBar.addTab(forecastTab, 1, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		 case R.id.action_update:
			 refreshForecast();
		 return true;

		case R.id.search:
			Intent intentSearch = new Intent(this, SearchActivity.class);
			Log.d("Search", "Search Start");
			startActivity(intentSearch);			
			return true;
			
		case R.id.search_with_gps:
			findGPSLocation();
			return true;
			
		case R.id.about:
			Intent intentAbout = new Intent(this, AboutActivity.class);
			startActivity(intentAbout);			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		switch (tab.getPosition()) {
		case 0:
			CurrentFragment currentFragment = new CurrentFragment();
			Log.d("Forecast", "create Current");
			ft.replace(R.id.frgmCont, currentFragment);
			break;

		case 1:
			ForecastFragment slidingFragment = new ForecastFragment();
			Log.d("Forecast", "create Sliding");
			ft.replace(R.id.frgmCont, slidingFragment);
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}
	
	private void findGPSLocation(){
		new FindLocation(this, new Notifier() {
			@Override
			public void operationFinished(Context context, int city, String cityNameStr) {
				Log.d("GPS", "city"+city);
				if(city!=0){
					SharedPreferences.Editor editor = sharedpreferences.edit();
			        editor.putInt(cityIDKey, city);
			        editor.putString(cityName, cityNameStr);
			        editor.commit();
			        refreshForecast();
				} else{
					Toast.makeText(context, getResources().getString(R.string.search_error),Toast.LENGTH_LONG).show();
				}
				
			}
		}).execute();	
	}
	
	private void refreshForecast() {
		if(isNetworkConnected()){
			sharedpreferences = getSharedPreferences(MyPREFERENCES,	Context.MODE_PRIVATE);
			if (sharedpreferences.contains("cityIDKey")) {
				cityID = sharedpreferences.getInt("cityIDKey", 0);
			}
			String locale = Locale.getDefault().getLanguage();
			new Connection(this, new Notifier() {
				@Override
				public void operationFinished(Context context, int city, String cityname) {
					setupTabs();
				}
			}).execute("http://xml.weather.co.ua/1.2/forecast/"+cityID+"?dayf=5&lang="+locale, "23.xml");
		} else {
			Toast.makeText(this, getResources().getString(R.string.download_error),Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		if (sharedpreferences.contains("cityIDKey")) {
			if(cityID!=sharedpreferences.getInt("cityIDKey", 0)){
				refreshForecast();
			}
		}
		super.onResume();
	}

	public void DownloadFromUrl(String DownloadUrl, String fileName) throws IOException {
		
			URL url = new URL(DownloadUrl); // you can write here any link
			long startTime = System.currentTimeMillis();
			Log.d("DownloadManager", "download begining");
			Log.d("DownloadManager", "download url:" + url);
			Log.d("DownloadManager", "downloaded file name:" + fileName);
			/* Open a connection to that URL. */
			String s;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					this.openFileOutput(fileName, MODE_PRIVATE)));
			while ((s = in.readLine()) != null) {
				bw.write(s);
			}
			bw.flush();
			bw.close();
			Log.d("DownloadManager",
					"download ready in"
							+ ((System.currentTimeMillis() - startTime) / 1000)
							+ " sec");
		
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
	
	private class Connection extends AsyncTask<String, Void, Exception> {
		private ProgressDialog progressDialog;
		private Context context;
		private Notifier notifier;
		private Exception exception;
		
		public Connection(Context context, Notifier notifier) {
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
            this.progressDialog.setTitle(getResources().getString(R.string.please_wait));
            this.progressDialog.setMessage(getResources().getString(R.string.loading_forecast));
            this.progressDialog.show();
        }					
		@Override
		protected Exception doInBackground(String... params) {
			try {
				DownloadFromUrl(params[0], params[1]);
			} catch (Exception e) {				
				exception=e;
			}
			return null;
		}
		@Override
		protected void onPostExecute(Exception result) {
			super.onPostExecute(result);
			if (result != null) {
				//this.progressDialog.dismiss();
				Log.d("DownloadManager", "Error: " + result);
				Toast.makeText(context, getResources().getString(R.string.download_error),Toast.LENGTH_LONG).show();
	         } else {
	        	 if (this.progressDialog.isShowing()) {
	                 this.progressDialog.dismiss();
	             } 
	             
	             if (notifier != null) {
	                 notifier.operationFinished(context, 0, "");
	             }
	         }	
			 
		}		
	}

	
}
