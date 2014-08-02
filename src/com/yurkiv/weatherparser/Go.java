package com.yurkiv.weatherparser;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xml.sax.InputSource;

import android.util.Log;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 06.04.11
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class Go {

    public static void main(String[] args) {

    	
    	
    	//Map<Integer, CountryCode> numericMap = new HashMap<Integer, CountryCode>();
    	
        //String[] countryCodes = Locale.getISOCountries();
    	String s="";
    	StringBuilder builder=new StringBuilder();
    	
    	File result = new File("D:/example.xml");
    	
        	int count=0;
            String xml=openUrl("http://xml.weather.co.ua/1.2/city/?country=804&lang=uk");
            Reader reader = new StringReader(xml);
            Persister serializer = new Persister();
        	try
            {
        		
                CityAll cityAll = serializer.read(CityAll.class, reader, false);
                List<CityAll.City> cityList=cityAll.getCities();
                for (CityAll.City city:cityList){
                	
                    //System.out.println(city.getName());
                    String url=openUrl("http://xml.weather.co.ua/1.2/forecast/"+Integer.toString(city.getId())+"?dayf=5&lang=en");
                    Reader readerurl = new StringReader(url);
                    Persister serializerurl = new Persister();
                    try
                    {

                    	ForecastCity f = serializerurl.read(ForecastCity.class, readerurl, false);
                        if (f.getCurrent()==null){
                        	System.out.println("Error------------");
                        } else{
                        	System.out.println(city.getName()+"==============================================================================");
                        	System.out.println("yes: "+f.getUrl());
                        	count++;
                        	Writer writer = new StringWriter();
                        	Serializer serializer1 = new Persister();
                        	
                        	serializer1.write(city, writer);
                        	builder.append(writer.toString()+"\n");
                        	System.out.println(builder.toString());
                        }
                        
                        

                    }   catch (org.simpleframework.xml.core.ValueRequiredException e)        {

                    	//System.out.println("Error"+e);


                    }   catch (Exception e)
                    {
                        //System.out.println("Error"+e);
                    }
                    
                }
                                
                PrintWriter out = new PrintWriter("D:/example.xml");
                out.println(builder.toString());
                
            }
            catch (Exception e)
            {
                //System.out.println("Error: "+ e.getMessage());
            }
        	if(count>0){
        		System.out.println(" : "+ count);
        	}
            
		        
        System.out.println("FINAL");
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

    public static String open(String fileName){
        BufferedReader in;
        String news=new String();
        String s;
        StringBuffer s2;
        try {
            in = new BufferedReader(new FileReader(fileName));
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

}
