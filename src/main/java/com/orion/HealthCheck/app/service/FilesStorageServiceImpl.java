package com.orion.HealthCheck.app.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.orion.HealthCheck.app.Config.ParseAll.ParseAllOf;
import static com.orion.HealthCheck.app.Config.ParseAll.ParseIP;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");
    
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
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}