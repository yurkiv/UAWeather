package com.yurkiv.weatherforecast;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.text.style.MetricAffectingSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yurkiv.weatherparser.Current;
import com.yurkiv.weatherparser.Day;
import com.yurkiv.weatherparser.Forecast;
import com.yurkiv.weatherparser.ForecastCity;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;;


public class SlidingFragment extends Fragment {
    
	private float scale;
    private LinearLayout llMain;    
    private Parser parser;
	private ForecastCity forecastCity;

        
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forecast, null);
        scale = getResources().getDisplayMetrics().density+0.5f;
        llMain=(LinearLayout) v.findViewById(R.id.llMain);

        Log.d("Forecast", "init forecast START");
		parser = new Parser();
		try {
			forecastCity = parser.getForecastCity(new InputStreamReader(getActivity().openFileInput("23.xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), getResources().getString(R.string.not_forecast),
					Toast.LENGTH_SHORT).show();
		}
		if (forecastCity == null) {
			Toast.makeText(getActivity(), getResources().getString(R.string.not_forecast),
					Toast.LENGTH_SHORT).show();
			return v;
		}
		Log.d("Forecast", "init forecast FINISH");
        
        Forecast forecast= forecastCity.getForecast();
        Map<String, List<Day>> map=forecast.getMap();
        List<String> dates=new ArrayList<String>();
        for (String s : map.keySet()){
            dates.add(s);
        }

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < dates.size(); i++) {
            llMain.addView(createDateTable(map.get(dates.get(i)), dates.get(i), i), lp);
            if (i<dates.size()-1){
                llMain.addView(createVerticalLine());
            }
        }
        return v;
    }

    private LinearLayout createDateTable(List<Day> days, String dateText, int index){

        LinearLayout dayView = new LinearLayout(getActivity());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dayView.setOrientation(LinearLayout.VERTICAL);
        dayView.setBackgroundColor(Color.TRANSPARENT);

        TextView tvDate=new TextView(getActivity());
        tvDate.setTextSize(getResources().getDimension(R.dimen.textsize20));
        tvDate.setTextColor(getResources().getColor(R.color.White));
        int pixels = (int) (30 * scale);
        tvDate.setHeight(pixels);
        tvDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        switch (index){
            case 0:
                tvDate.setBackgroundColor(Color.rgb(255,187,51));
                tvDate.setText(getDateText(dateText, 0));
                break;
            case 1:
                tvDate.setBackgroundColor(Color.rgb(0,153,204));
                tvDate.setText(getDateText(dateText, 1));
                break;
            default:
                tvDate.setBackgroundColor(Color.rgb(0,153,204));
                tvDate.setText(getDateText(dateText, 1));
        }

        LinearLayout row = new LinearLayout(getActivity());
        row.setOrientation(LinearLayout.HORIZONTAL);
        for(Day day : days){
            LayoutParams lpp =  new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            row.addView(createForecastLayout(day), lpp);
            if (!(day==days.get(days.size()-1))){
                row.addView(createVerticalLine());
            }
    	}
        dayView.addView(tvDate);
        dayView.addView(row, lp);

    	return dayView;
    }


    private LinearLayout createForecastLayout(Day day){
    	LinearLayout llForecast=new LinearLayout(getActivity());
    	llForecast.setOrientation(LinearLayout.VERTICAL);
    	llForecast.setPadding(0, 0, 0, 0);
    	llForecast.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams lpnew = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lpnew.weight = 1.0f;


        TextView f1=new TextView(getActivity());
        LayoutParams params111=new LayoutParams( (int)(80*scale), LayoutParams.WRAP_CONTENT);
        
        f1.setLayoutParams(params111);
        f1.setBackgroundResource(R.color.trans30);
        f1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        f1.setTextSize(getResources().getDimension(R.dimen.textsize16));
        int pixels = (int) (20 * scale);
        //f1.setHeight(pixels);
        boolean isDay=true;
        String hourText="";
        switch (day.getHour()){
            case 3:
                hourText=getResources().getString(R.string.time_night);
                isDay=false;
                break;
            case 9:
                hourText=getResources().getString(R.string.time_morning);
                isDay=true;
                break;
            case 15:
                hourText=getResources().getString(R.string.time_afternoon);
                isDay=true;
                break;                
            case 21:
                hourText=getResources().getString(R.string.time_evening);
                isDay=true;
                break;                
        }
    	f1.setText(hourText);
        llForecast.addView(f1);
        
      //============================================================================================
               
        ImageView imageView3=new ImageView(getActivity()); 
        int cloud=day.getCloud();
        int image=R.drawable.n11_default_weather_icon;
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
        imageView3.setImageResource(image); 
        llForecast.addView(imageView3);
    	//============================================================================================
    	LinearLayout forecast4=new LinearLayout(getActivity());
        forecast4.setOrientation(LinearLayout.HORIZONTAL);
        forecast4.setBackgroundColor(Color.TRANSPARENT);        
    	forecast4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	forecast4.setLayoutParams(lpnew);        
    	ImageView imageView4=new ImageView(getActivity());
        imageView4.setImageResource(R.drawable.temperature);        
        TextView f4=new TextView(getActivity());        
        f4.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        f4.setTextSize(getResources().getDimension(R.dimen.textsize14));     
        f4.setTextColor(getResources().getColor(R.color.White));        
        f4.setLines(2);
        Day.Temperature temperature=day.getTemperature();
        String minT=temperature.getMin()+"\u00B0";   
        String maxT=temperature.getMax()+"\u00B0";
        f4.setText(maxT+"\n"+minT);                
        forecast4.addView(imageView4);
        forecast4.addView(f4);        
        llForecast.addView(forecast4);
    	//============================================================================================
        LinearLayout forecast1=new LinearLayout(getActivity());
        forecast1.setOrientation(LinearLayout.HORIZONTAL);
        forecast1.setBackgroundColor(Color.TRANSPARENT);        
    	forecast1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	forecast1.setLayoutParams(lpnew);        
    	ImageView imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.umbrella);        
        TextView f2=new TextView(getActivity());        
        f2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        f2.setTextSize(getResources().getDimension(R.dimen.textsize14));     
        f2.setTextColor(getResources().getColor(R.color.White));        
        f2.setText(Integer.toString(day.getPpcp())+"%");                
        forecast1.addView(imageView);
        forecast1.addView(f2);        
        llForecast.addView(forecast1);
      //============================================================================================
        LinearLayout forecast5=new LinearLayout(getActivity());
        forecast5.setOrientation(LinearLayout.HORIZONTAL);
        forecast5.setBackgroundColor(Color.TRANSPARENT);        
    	forecast5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	forecast5.setLayoutParams(lpnew);        
    	ImageView imageView5=new ImageView(getActivity());
        imageView5.setImageResource(R.drawable.pressure);        
        TextView f5=new TextView(getActivity());        
        f5.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        f5.setTextSize(getResources().getDimension(R.dimen.textsize14));     
        f5.setTextColor(getResources().getColor(R.color.White));        
        f5.setLines(2);
        Day.Pressure pressure=day.getPressure();
        int press=(pressure.getMin()+pressure.getMax())/2;
        f5.setText(press+"\n"+getResources().getString(R.string.pressure));              
        forecast5.addView(imageView5);
        forecast5.addView(f5);        
        llForecast.addView(forecast5);
      //============================================================================================
        LinearLayout forecast6=new LinearLayout(getActivity());
        forecast6.setOrientation(LinearLayout.HORIZONTAL);
        forecast6.setBackgroundColor(Color.TRANSPARENT);        
    	forecast6.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	forecast6.setLayoutParams(lpnew);        
    	ImageView imageView6=new ImageView(getActivity());
        imageView6.setImageResource(R.drawable.wind);        
        TextView f6=new TextView(getActivity());        
        f6.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        f6.setTextSize(getResources().getDimension(R.dimen.textsize14));     
        f6.setTextColor(getResources().getColor(R.color.White));        
        f6.setLines(2);
                     
        Day.Wind wind=day.getWind();
        int windSpeed=(wind.getMin()+wind.getMax())/2;
        int rumbw=wind.getRumb();
        f6.setText(getWindRumb(rumbw)+"\n"+windSpeed+getResources().getString(R.string.wind_speed));       
                 
        forecast6.addView(imageView6);
        forecast6.addView(f6);        
        llForecast.addView(forecast6);     
        
        return llForecast;
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

    private View createVerticalLine(){
        View line=new View(getActivity());
        LayoutParams params=new LayoutParams(1, LayoutParams.MATCH_PARENT);

        line.setLayoutParams(params);
        line.setBackgroundResource(R.color.trans30);
        //line.setBackgroundColor(Color.DKGRAY);
        return line;
    }

    private String getDateText(String dateText, int code) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String weekDay="";
        try {
            cal.setTime(sdf.parse(dateText));
        } catch (ParseException e) {
            e.printStackTrace();
            return weekDay;
        }
        
        SimpleDateFormat dayFormat;
        dayFormat= new SimpleDateFormat("E dd.MM.yyyy", Locale.getDefault());
        if (code==0){
            dayFormat= new SimpleDateFormat("E", Locale.getDefault());
        }
        weekDay = dayFormat.format(cal.getTime());
        return weekDay;
    }       
    
}