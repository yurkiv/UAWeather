package com.yurkiv.weatherforecast;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.yurkiv.weatherparser.Current;
import com.yurkiv.weatherparser.ForecastCity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zloysalat on 19.07.2014.
 */
public class CurrentFragment extends Fragment {
	
	private Parser parser;
	private ForecastCity forecastCity;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	View v =  inflater.inflate(R.layout.current, null);       
    	
    	Log.d("Forecast", "init forecast START");
		parser = new Parser();
		try {
			forecastCity = parser.getForecastCity(new InputStreamReader(getActivity().openFileInput("23.xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getResources().getString(R.string.not_forecast),Toast.LENGTH_SHORT).show();
		}
		if (forecastCity == null) {
			Toast.makeText(getActivity(), getResources().getString(R.string.not_forecast),Toast.LENGTH_SHORT).show();
			return v;
		}		
		Log.d("Forecast", "init forecast FINISH");
        
        Current current=forecastCity.getCurrent();
        
        TextView lastUpdated=(TextView) v.findViewById(R.id.textViewLastUpdated);
        lastUpdated.setText(getResources().getString(R.string.last_updated)+" "+getLastUpdatedTime(current.getLastUpdated()));  
        
        TextView temp=(TextView) v.findViewById(R.id.textViewT);
        temp.setText(current.getT()+"\u00B0");    	
        
        TextView tempComf=(TextView) v.findViewById(R.id.textViewTComf);
        tempComf.setText(current.getT_flik()+"\u00B0");  
        
        ImageView cloud=(ImageView) v.findViewById(R.id.imageViewCloud);
        cloud.setImageResource(getCloudImage(current.getTime(), current.getCloud()));
             
        TextView cityName=(TextView) v.findViewById(R.id.textViewCity);
        cityName.setText(forecastCity.getCity().getName());  
                
        TextView P=(TextView) v.findViewById(R.id.TextViewP);
        P.setText(Integer.toString(current.getP())+" "+getResources().getString(R.string.pressure));  
        
        TextView WSpeed=(TextView) v.findViewById(R.id.TextViewWSpeed);
        WSpeed.setText(Integer.toString(current.getW())+" "+getResources().getString(R.string.wind_speed));  
        
        TextView WRumb=(TextView) v.findViewById(R.id.TextViewWRumb);
        WRumb.setText(getWindRumb(current.getW_rumb())+" ("+Integer.toString(current.getW_rumb())+"\u00B0)");
        
        TextView H=(TextView) v.findViewById(R.id.textViewH);
        H.setText(Integer.toString(current.getH())+" %");  
    	
        return v;
    }
    
    private String getLastUpdatedTime(String time){
    	String last_updated="";    	
    	Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        try {
            cal.setTime(sdf.parse(time.replaceAll("\\p{Cntrl}", "")));
        } catch (ParseException e) {
            e.printStackTrace();
            return last_updated;
        }        
        SimpleDateFormat dayFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());        
        last_updated=dayFormat.format(cal.getTime());
    	return last_updated;
    }
          
    private int getCloudImage(String dateText, Integer cloud){
    	int image=R.drawable.n11_default_weather_icon;
        
    	Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        try {
            cal.setTime(sdf.parse(dateText.replaceAll("\\p{Cntrl}", "")));
        } catch (ParseException e) {
            e.printStackTrace();
            return image;
        }        
        SimpleDateFormat dayFormat= new SimpleDateFormat("HH", Locale.getDefault());        
        int hour=Integer.parseInt(dayFormat.format(cal.getTime()));
        Log.d("Forecast", Integer.toString(hour));
        boolean isDay=true;
        
        if (hour>=21 || hour<=6){
        	isDay=false;
        }
            	
    	if (isDay){
        	if (cloud>=0 && cloud<10){
            	image=R.drawable.d0_clear;
        	} else if(cloud>=10 && cloud<20){
        		image=R.drawable.d1_partly_cloudy;
        	} else if(cloud>=20 && cloud<30){
        		image=R.drawable.d2_mostly_cloudy;
        	} else if(cloud>=30 && cloud<40){
        		image=R.drawable.d3_cloudy;
        	} else if(cloud>=40 && cloud<50){
        		image=R.drawable.d4_light_rain;
        	} else if(cloud>=50 && cloud<60){
        		image=R.drawable.d5_rain;
        	} else if(cloud>=60 && cloud<70){
        		image=R.drawable.d6_thunderstorms;
        	} else if(cloud>=70 && cloud<80){
        		image=R.drawable.d7_hail;
        	} else if(cloud>=80 && cloud<90){
        		image=R.drawable.d8_mixed_rain_and_snow;
        	} else if(cloud>=90 && cloud<100){
        		image=R.drawable.d9_snow;
        	} else if(cloud>=100 && cloud<110){
        		image=R.drawable.d10_heavy_snow;
        	}     
        } else {
        	if (cloud>=0 && cloud<10){
            	image=R.drawable.n0_clear;
        	} else if(cloud>=10 && cloud<20){
        		image=R.drawable.n1_partly_cloudy;
        	} else if(cloud>=20 && cloud<30){
        		image=R.drawable.n2_mostly_cloudy;
        	} else if(cloud>=30 && cloud<40){
        		image=R.drawable.n3_cloudy;
        	} else if(cloud>=40 && cloud<50){
        		image=R.drawable.n4_light_rain;
        	} else if(cloud>=50 && cloud<60){
        		image=R.drawable.n5_rain;
        	} else if(cloud>=60 && cloud<70){
        		image=R.drawable.n6_thunderstorms;
        	} else if(cloud>=70 && cloud<80){
        		image=R.drawable.n7_hail;
        	} else if(cloud>=80 && cloud<90){
        		image=R.drawable.n8_mixed_rain_and_snow;
        	} else if(cloud>=90 && cloud<100){
        		image=R.drawable.n9_snow;
        	} else if(cloud>=100 && cloud<110){
        		image=R.drawable.n10_heavy_snow;
        	}     
        }
        return image;
    }
    
    private String getWindRumb(int rumb){
    	String rumbText=getResources().getString(R.string.N);
        if (rumb>0 && rumb<=23){
            rumbText=getResources().getString(R.string.N);
        } else if (rumb>23 && rumb<=68){
        	rumbText=getResources().getString(R.string.NE);
        } else if (rumb>68 && rumb<=113){
        	rumbText=getResources().getString(R.string.E);
        } else if (rumb>113 && rumb<=158){
        	rumbText=getResources().getString(R.string.SE);
        } else if (rumb>158 && rumb<=203){
        	rumbText=getResources().getString(R.string.S);
        } else if (rumb>203 && rumb<=248){
        	rumbText=getResources().getString(R.string.SW);
        } else if (rumb>248 && rumb<=293){
        	rumbText=getResources().getString(R.string.W);
        } else if (rumb>293 && rumb<=338){
        	rumbText=getResources().getString(R.string.NW);
        } else if (rumb>338 && rumb<=360){
        	rumbText=getResources().getString(R.string.N);
        }
        return rumbText;
    }

}
