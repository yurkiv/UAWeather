package com.yurkiv.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.yurkiv.weatherparser.CityAll;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends ActionBarActivity{
    private ListView listView;
    String selectCity;
    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String cityName = "cityNameKey";
    private static final String cityIDKey = "cityIDKey";
    private SharedPreferences sharedpreferences;

    private ArrayAdapter<String> adapter;
    private int locationID;
    OnQueryTextListener listener;
    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        listView= (ListView) findViewById(R.id.listView);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(cityName)) {
        	selectCity=sharedpreferences.getString(cityName, "");
        }

       locale=Locale.getDefault().getLanguage();
       InputStream is = getResources().openRawResource(R.raw.city_en);
       Log.d("Locale", "Locale: "+locale);
       if(locale.equals("ru")){
    	   	Log.d("Locale", "Locale: "+locale);
        	is = getResources().openRawResource(R.raw.city_ru);
        } else if(locale.equals("uk")){
        	Log.d("Locale", "Locale: "+locale);
        	is = getResources().openRawResource(R.raw.city_uk);
        }        
        
        String xml=Parser.openIS(is);
        Reader reader = new StringReader(xml);
        
        Persister serializer = new Persister();
        try {
            CityAll cityAll = serializer.read(CityAll.class, reader, false);
            final List<CityAll.City> cityList=cityAll.getCities();
            final HashMap<String, Integer> idCityName=new HashMap<String, Integer>();
            String[] products=new String[cityList.size()];
            for (int i = 0; i < cityList.size(); i++) {
                products[i]=cityList.get(i).getName();
                idCityName.put(cityList.get(i).getName(), cityList.get(i).getId());
            }
            // Adding items to listview
            adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.city_name, products);
            listView.setAdapter(adapter);            
            listener=new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String arg0) {
					SearchActivity.this.adapter.getFilter().filter(arg0);
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String arg0) {
					SearchActivity.this.adapter.getFilter().filter(arg0);
					return false;
				}
			};

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	selectCity=adapter.getItem(position);
                    locationID=idCityName.get(adapter.getItem(position).toString());
                    savePreferences();
                    finish();
                }
            });
        }
        catch (Exception e){
        	Toast.makeText(this, getResources().getString(R.string.not_city_list),Toast.LENGTH_SHORT).show();
        	Log.d("DownloadManager", "Error: " + e);
        }
    }

    private void savePreferences(){
        String n  = selectCity;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(cityName, n);
        editor.putInt(cityIDKey, locationID);
        editor.commit();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.queryhint));
        searchView.setOnQueryTextListener(listener);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
                return true;
    }
    
}
