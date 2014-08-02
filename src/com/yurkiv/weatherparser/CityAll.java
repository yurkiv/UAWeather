package com.yurkiv.weatherparser;

import org.simpleframework.xml.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 10.04.11
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
@Root(name="city")
public class CityAll {
    @Attribute(name="version")
    private String version;
    @Attribute(name="last_updated")
    private String last_updated;
    @ElementList(name="city", inline=true)
    private List<City> cityList;

    public String getVersion(){
        return version;
    }

    public String getLast_updated(){
        return last_updated;
    }

    public List<City> getCities()
    {
        return cityList;
    }
    
    public void addCities(List<City> cities)
    {
         this.cityList=cities;
    }

    public String toString(){
        return "CountryAll:\n"+
               "Version:"+getVersion()+"\n"+
               "Last_updated:"+getLast_updated()+"\n"+
               "Cities:"+getCities();
    }

    @Root(name="city")
    public static class City{
        @Attribute(name="id")
        private int id;
        @Element(name="name")
        private String name;
        @Element(name="name_en")
        private String name_en;
//        @Element(name="region" )
//        private String region;
        @Element(name="country")
        private String country;
        @Element(name="country_id")
        private int country_id;

        public Integer getId(){
                return id;
            }

        public String getCountry(){
                return country;
            }

        public Integer getCountry_id(){
                return country_id;
            }

        public String getName(){
                return name;
            }

        public String getName_en(){
                return name_en;
            }

//        public String getRegion(){
//            return region;
//        }

        public String toString(){
            return "Country:\n"+
                    "Id:"+getId()+"\n"+
                    "Country:"+getCountry()+"\n"+
                    "Country_id"+getCountry_id()+"\n"+
                    "Name:"+getName()+"\n"+
                    "Name_en:"+getName_en()+"\n"+
//                    "Region:"+getRegion()+"\n"+
                    "----------------------------\n";
        }
    }
}
