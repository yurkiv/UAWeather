package com.yurkiv.lol;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zloysalat on 20.07.2014.
 */
public class Test {
    public static void main(String[] args) {


        DownloadFromUrl("http://xml.weather.co.ua/1.2/forecast/23?dayf=5&lang=uk", "23.xml");

    }

    public static void DownloadFromUrl(String DownloadUrl, String fileName) {
        try {
            //File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File ("D:/your_path");
            if(dir.exists()==false) {
                dir.mkdirs();
            }
            URL url = new URL(DownloadUrl); //you can write here any link
            File file = new File(dir, fileName);
            long startTime = System.currentTimeMillis();
            System.out.println("download begining");
            System.out.println("download url:" + url);
            System.out.println( "downloaded file name:" + fileName);
           /* Open a connection to that URL. */
            String s;
            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            while ((s = in.readLine()) != null) {
                bw.write(s);
            }
            bw.flush();
            bw.close();
            System.out.println("download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
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
}
