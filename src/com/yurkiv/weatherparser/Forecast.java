package com.yurkiv.weatherparser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: misha
 * Date: 07.04.11
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
@Root(name="forecast")
public class Forecast {

    @ElementList(entry="day", inline=true)
    private List<Day> dayList;


    public List<Day> getDay()
    {
        return dayList;
    }

    public String toString(){
        return "Forecast:\n"+dayList.toString();
    }

    public Map<String, List<Day>> getMap(){
        HashMap<String, List<Day>> hashMap=new HashMap<String, List<Day>>();
        for (Day day:dayList){
            if (!hashMap.containsKey(day.getDate())){
                List<Day> days=new ArrayList<Day>();
                days.add(day);
                hashMap.put(day.getDate(), days);
            } else {
                hashMap.get(day.getDate()).add(day);
            }
        }
        Map<String, List<Day>> map = new TreeMap<String, List<Day>>(hashMap);
        return map;
    }

}
