package com.orion.HealthCheck.app.Config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@EnableWebSecurity
@RestController
@RequestMapping("/logs")
public class LogParse extends ParseAll {
    @CrossOrigin("http://localhost:3000/")
    @GetMapping
    public List<String> Parser(String name) throws IOException {

        ArrayList<String> allIpAdress = new ArrayList<String>();
        ParseIP(allIpAdress, name);
        ArrayList<String> listeninKrali = new ArrayList<String>();
        ArrayList<String> heapMemoryUsed = new ArrayList<String>();
        ArrayList<String> physicalMemoryFree = new ArrayList<String>();
        ArrayList<String> swapSpaceTotal = new ArrayList<String>();
        String[] a = null;
        ArrayList<String> toplu = new ArrayList<String>();
        //ParseAllOf("heap.memory", heapMemoryUsed);
        ParseAllOf("physical.memory.total", heapMemoryUsed, name);
        ParseAllOf("heap.memory", listeninKrali, name);
        ParseAllOf("physical.memory.free", physicalMemoryFree, name);
        ParseAllOf("swap.space.total", swapSpaceTotal, name);
        for (int i = 0; i < heapMemoryUsed.size(); i++) {
            toplu.add(allIpAdress.get(i) + heapMemoryUsed.get(i) + physicalMemoryFree.get(i) + swapSpaceTotal.get(i));

        }



        //a = String.valueOf(ParseAllOf("heap.memory.used").charAt(2));





           /* BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\fguler\\Downloads\\HealthCheck\\src\\main\\java\\com\\orion\\HealthCheck\\app\\Config\\server.log")));
            String IP = null;
            String line;
            int countWord = 0;
            int sentenceCount = 0;
            int characterCount = 0;
            int paragraphCount = 1;
            int whitespaceCount = 0;
            ArrayList LİSTE = null;
            int b = 0;
            String result = null;
            ArrayList<String> wordArrayList = new ArrayList<String>();
            ArrayList<String> memoryler = new ArrayList<String>();
            String keyword = "physical.memory.free=";
            String line;
            while ((line = br.readLine()) != null) {
                int startIndex = line.indexOf(keyword); // kelimenin başlangıç indeksi
                int endIndex = line.indexOf(",", startIndex); // virgülün indeksi
                if (line.equals("")) {
                    paragraphCount++;
                }else if(line.contains("IP")){

                    wordArrayList.add("İP".toString());
                    b= +1;
                }/*else if(startIndex != -1 && endIndex != -1) {
                    result = line.substring(startIndex + keyword.length(), endIndex).trim();
                    memoryler.add("physical.memory.free  " + result);
                    //System.out.println(result);
                }else {
                    characterCount += line.length();

                    String[] wordList = line.split("\\s+");

                    countWord += wordList.length;

                    whitespaceCount += countWord - 1;

                    String[] sentenceList = line.split("[!?.:]+");

                    sentenceCount += sentenceList.length;
                }

            }




            br.close();

            System.out.println("Total number of words = " + countWord);

            System.out.println("Total number of sentences = " + sentenceCount);

            System.out.println("Total number of characters = " + characterCount);

            System.out.println("Total number of paragraphs = " + paragraphCount);

            System.out.println("Total number of whitespaces = " + whitespaceCount); */

        return toplu;
    }


}
