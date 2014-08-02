package com.yurkiv.weatherparser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 06.04.11
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */


//прогноз на конкрений день для міста, є частино прогнозу для міста
@Root(name="day")
public class Day {
    @Attribute(name="date")
    private String date;
    @Attribute(name="hour")
    private int hour;
    @Element(name="cloud")
    private int cloud;
    @Element(name="pict")
    private String pict_cloud;
    @Element(name="ppcp")
    private int ppcp; //-------------------
    @Element(name="t")
    private Temperature temperature;
    @Element(name="p")
    private Pressure pressure;
    @Element(name="wind")
    private Wind wind;
    @Element(name="hmid")
    private Hmid hmid;
    @Element(name="wpi")
    private int wpi;

    public String getDate(){
        return date;
    }

    public Integer getHour(){
        return hour;
    }

    public Integer getCloud(){
        return cloud;
    }

    public String getPict_cloud(){
        return pict_cloud;
    }

    public Integer getPpcp(){
        return ppcp;
    }

    public String getTemperatureString(){
        return temperature.toString();
    }

    public Temperature getTemperature(){
        return temperature;
    }

    public String getPressureString(){
        return pressure.toString();
    }

    public Pressure getPressure(){
        return pressure;
    }

    public String getWindString(){
        return wind.toString();
    }

    public Wind getWind(){
        return wind;
    }

    public String getHmidString(){
        return hmid.toString();
    }

    public Hmid getHmid(){
        return hmid;
    }

    public Integer getWpi(){
        return wpi;
    }

    public String toString(){
        return "Day: "+
               "Date= " + getDate()+"\n"+
               "Hour= " + getHour()+"\n"+
               "Cloud= " + getCloud()+"\n"+
               "Pict= " + getPict_cloud()+"\n"+
               "Ppcp= " + getPpcp()+"\n"+
               getTemperatureString()+"\n"+
               getPressureString()+"\n"+
               getWindString()+"\n"+
               getHmidString()+"\n"+
               "Wpi= " + getWpi()+"\n"+
                "---------------------------------\n";
    }


    @Root(name="t")
    public static class Temperature {

        @Element(name="min")
        private int min;
        @Element(name="max")
        private int max;


        public int getMin()
        {
            return min;
        }

        public int getMax()
        {
            return max;
        }

        public String toString(){
            return "T: min="+min+" max="+max;
        }
    }

    @Root(name="p")
    public static class Pressure{
        @Element(name="min")
        private int min;
        @Element(name="max")
        private int max;


        public int getMin()
        {
            return min;
        }

        public int getMax()
        {
            return max;
        }

        public String toString(){
            return "P: min="+min+" max="+max;
        }
    }

    @Root(name="wind")
    public static class Wind{
        @Element(name="min")
        private int min;
        @Element(name="max")
        private int max;
        @Element(name="rumb")
        private int rumb;

        public int getMin()
        {
            return min;
        }

        public int getMax()
        {
            return max;
        }

        public int getRumb()
        {
            return rumb;
        }

        public String toString(){
            return "Wind: min="+min+" max="+max+" rumb="+rumb;
        }
    }

    @Root(name="hmid")
    public static class Hmid{
        @Element(name="min")
        private int min;
        @Element(name="max")
        private int max;


        public int getMin()
        {
            return min;
        }

        public int getMax()
        {
            return max;
        }

        public String toString(){
            return "Hmid: min="+min+" max="+max;
        }
    }

}
