package com.yurkiv.weatherparser;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pogoda {

      public static void main(String args[]){
        System.out.println("Hello World!");
        String t="";
        t=GetTempr("http://xml.weather.co.ua/1.2/forecast/23?dayf=5&lang=uk");
        System.out.println(t+"*");

    }


       public static String GetTempr(String urlsite){
        String matchtemper="";
        try{
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(
                    conn.getInputStream());
            StringBuilder allpage = new StringBuilder();
            int n=0;
            char[] buffer=new char[40000];
            while (n>=0){
                n=rd.read(buffer, 0, buffer.length);
                if (n>0){
                    allpage.append(buffer, 0, n);
                }
            }
           // "<span class=\"temperature\">+9</span>"

            //("<span style=\"color:#[a-zA-Z0-9]+\">[^-+0]+([-+0-9]+)[^<]+" +
             //       "</span>[^(а-яА-ЯёЁa-zA-Z0-9)]+([а-яА-ЯёЁa-zA-Z ]+)")

            final Pattern pattern = Pattern.compile("[0-9]");

            Matcher matcher = pattern.matcher(allpage.toString());
            System.out.println(matcher);
            if (matcher.find()){
                matchtemper=matcher.group(0);
            }
            System.out.println(matchtemper);
            return matchtemper;
        }
        catch (Exception e){
            //ploho
        }
        System.out.println(matchtemper);
        return matchtemper;
    }

}