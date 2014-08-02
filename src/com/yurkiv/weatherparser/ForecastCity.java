package com.yurkiv.weatherparser;

import java.io.Serializable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 10.04.11
 * Time: 18:43
 * To change this template use File | Settings | File Templates.
 */
@Root(name="forecast")
public class ForecastCity implements Serializable{
    @Attribute(name="version")
    private String version;
    @Attribute(name="last_updated")
    private String last_updated;
    @Element(name="url")
    private String url;
    @Element(name="city")
    private City city;
    @Element(name="current", required = true)
    private Current current;

    public ForecastCity() {
    }

    public City getCity() {
        return city;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    @Element(name="forecast")
    private Forecast forecast;

    public String getVersion(){
    return version;
    }

    public String getLast_updated(){
        return last_updated;
    }

    public String getUrl(){
        return url;
    }

    public String toString(){
        return "ForecastCity:\n"+
               "Version:"+getVersion()+"\n"+
               "Last_updated:"+getLast_updated()+"\n"+
               "URL:"+getUrl()+"\n"+
               "+++++++++++++++++++++++++++++++++++++++++++++\n"+
               city.toString()+
               "+++++++++++++++++++++++++++++++++++++++++++++\n"+
               //current.toString()+
               "+++++++++++++++++++++++++++++++++++++++++++++\n"+
               forecast.toString();
    }

    private class EmptyElementConverter implements Converter<Current> {

        @Override
        public Current read(InputNode inputNode) throws Exception {
            return null;
        }

        @Override
        public void write(OutputNode outputNode, Current current) throws Exception {
            outputNode.setValue(null);
        }
    }
}
