package com.yurkiv.weatherparser;

import org.simpleframework.xml.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 10.04.11
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
@Root(name="country")
public class CountryAll {
    @Attribute(name="version")
    private String version;
    @Attribute(name="last_updated")
    private String last_updated;
    @ElementList(name="country", inline=true)
    private List<Country> countryList;

    public String getVersion(){
        return version;
    }

    public String getLast_updated(){
        return last_updated;
    }

    public List<Country> getCountries()
    {
        return countryList;
    }

    public String toString(){
        return "CountryAll:\n"+
               "Version:"+getVersion()+"\n"+
               "Last_updated:"+getLast_updated()+"\n"+
               "Country:"+getCountries();
    }

    @Root(name="country")
    private static class Country{
        @Attribute(name="id")
        private int id;
        @Element(name="ISO2")
        private String ISO2;
        @Element(name="ISO3")
        private String ISO3;
        @Element(name="name")
        private String name;
        @Element(name="name_en")
        private String name_en;
        @Element(name="region")
        private Region region;

        public Integer getId(){
                return id;
            }

        public String getISO2(){
                return ISO2;
            }

        public String getISO3(){
                return ISO3;
            }

        public String getName(){
                return name;
            }

        public String getName_en(){
                return name_en;
            }

        public Region getRegion(){
            return region;
        }

        public String toString(){
            return "Country:\n"+
                    "Id:"+getId()+"\n"+
                    "ISO2:"+getISO2()+"\n"+
                    "ISO3:"+getISO3()+"\n"+
                    "Name:"+getName()+"\n"+
                    "Name_en:"+getName_en()+"\n"+
                    getRegion()+"\n"+
                    "----------------------------\n";
        }

        @Root(name="region")
        private static class Region{
            @Attribute(name="id")
            private int id;
            @Text
            private String name;

            public Integer getId(){
                return id;
            }

            public String getName(){
                return name;
            }

            public String toString(){
                return "Region:"+getName()+"\n"+
                       "Id:"+getId();
            }
        }
    }
}
