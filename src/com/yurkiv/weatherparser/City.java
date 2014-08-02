package com.yurkiv.weatherparser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 07.04.11
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */

@Root(name="city")
public class City {
    @Attribute(name="id")
    private int id;
    @Element(name="name")
    private String name;
    @Element(name="name_en")
    private String name_en;
    @Element(name="region")
    private Region region;
    @Element(name="country")
    private Country country;

    public Integer getId(){
        return id;
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

    public Country getCountry(){
        return country;
    }

    public String toString(){
        return "City: "+
               "Id= " + getId()+"\n"+
               "Name= " + getName()+"\n"+
               "Name_en= " + getName_en()+"\n"+
               getRegion()+"\n"+
               getCountry()+"\n"+
               "---------------------------------\n";
    }

    @Root(name="region")
    private static class Region {

        @Element(name="name")
        private String name;
        @Element(name="name_en")
        private String name_en;

        public String getName()
        {
            return name;
        }

        public String getName_en()
        {
            return name_en;
        }

        public String toString(){
            return "Region: name="+name+" neme_en="+name_en;
        }
    }

    @Root(name="country")
    private static class Country {
        @Attribute(name = "id")
        private int id;
        @Element(name="name")
        private String name;
        @Element(name="name_en")
        private String name_en;

        public String getName()
        {
            return name;
        }

        public String getName_en()
        {
            return name_en;
        }

        public Integer getId()
        {
            return id;
        }

        public String toString(){
            return "Region: name="+name+" neme_en="+name_en+" id="+id;
        }
    }
}
