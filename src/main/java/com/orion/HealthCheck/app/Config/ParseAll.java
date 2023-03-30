package com.orion.HealthCheck.app.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseAll {




    public static String ParseIP(ArrayList<String> arrayList, String name) throws IOException{


        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\fguler\\Downloads\\HealthCheck\\uploads\\" + name));
            
        	String line;
            while ((line = br.readLine()) != null) {
                String regex = "(\\d{1,3}\\.){3}\\d{1,3}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    arrayList.add(matcher.group());
                }
            }
            br.close();
         
        
        return arrayList.toString() ;
    }

    public static String ParseAllOf(String keyStartIndex, ArrayList<String> arrayList, String name) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\fguler\\Downloads\\HealthCheck\\uploads\\" + name)));
        String IP = null;
        String line;
        ArrayList LİSTE = null;
        int b = 0;
        int paragraphCount = 0;
        String result = null;
        ArrayList<String> wordArrayList = new ArrayList<String>();
        //memoryler = new ArrayList<String>();
        String keyword = keyStartIndex;
        while ((line = br.readLine()) != null) {
            int startIndex = line.indexOf(keyword); // kelimenin başlangıç indeksi
            int endIndex = line.indexOf(",", startIndex); // virgülün indeksi
            if (line.equals("")) {
                paragraphCount++;
            } else if (line.contains("IP")) {

                wordArrayList.add("İP".toString());
                //b = +1;
            } else if (startIndex != -1 && endIndex != -1) {
                result = line.substring(startIndex + keyword.length(), endIndex).trim();
                arrayList.add(" [ " + keyStartIndex + result +" ] ");

            }
        }
        return arrayList.toString();
    }


}
