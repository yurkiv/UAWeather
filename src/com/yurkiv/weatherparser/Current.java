package com.yurkiv.weatherparser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 07.04.11
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */


//Вигружає теперішню погоду для конкретного міста
//є частиною прогнозу для конкретного міста
@Root(name="current")
public class Current {
    @Attribute(name="last_updated")
    private String last_updated;
    @Attribute(name="expires")
    private String expires;
    @Element(name="time")
    private String time;
    @Element(name="cloud")
    private int cloud;
    @Element(name="pict")
    private String pict_cloud;
    @Element(name="t")
    private String t;
    @Element(name="t_flik")
    private String t_flik;
    @Element(name="p")
    private int p;
    @Element(name="w")
    private int w;
    @Element(name="w_gust")
    private int w_gust;
    @Element(name="w_rumb")
    private int w_rumb;
    @Element(name="h")
    private int h;


    public String getLastUpdated(){
        return last_updated;
    }

//     public String getExpires(){
//        return expires;
//    }

    public String getTime(){
        return  time;
    }

    public Integer getCloud(){
        return cloud;
    }

    public String getPict_cloud(){
        return pict_cloud;
    }

    public String getT(){
        return t;
    }

    public String getT_flik(){
        return t_flik;
    }

    public Integer getP(){
        return p;
    }

    public Integer getW(){
        return w;
    }

    public Integer getW_gust(){
        return w_gust;
    }

    public Integer getW_rumb(){
        return w_rumb;
    }

    public Integer getH(){
        return h;
    }

    public String toString(){
        return "Curent: "+
               "Last_updated= " + getLastUpdated()+"\n"+
               //"Expires= " + getExpires()+"\n"+
               "Time= " + getTime()+"\n"+
               "Cloud= " + getCloud()+"\n"+
               "Pict= " + getPict_cloud()+"\n"+
               "T= " + getT()+"\n"+
               "T_flik= " + getT_flik()+"\n"+
               "P= " + getP()+"\n"+
               "W= " + getW()+"\n"+
               "W_gust= " + getW_gust()+"\n"+
               "W_rumb= " + getW_rumb()+"\n"+
               "H= " + getH()+"\n"+
                "---------------------------------\n";
    }

 }