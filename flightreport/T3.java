package flightreport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class T3{
    
    static String Splitter = ",";
    static String line;
    
    public static void main(String[] args) throws FileNotFoundException{
        T3 o = new T3();
        HashMap<String, ArrayList> flightMap = new HashMap();
        String flights = "flights.csv";
        Scanner sc  = new Scanner(System.in);
        System.out.println("Please enter a directory to export the files:\nExample:\tC:\\Users\\UserName\\Desktop\\FolderName\\");
        String directory = sc.next();
        long startTime = System.currentTimeMillis();
        long startTimeProcessing = System.currentTimeMillis();
        o.addFlightsToMaps(flights, flightMap);
        long stopTimeProcessing = System.currentTimeMillis();
        System.out.println("\nProcessing Time: "+(stopTimeProcessing-startTimeProcessing)/1000+" seconds");
        o.writeFlight(directory, flightMap);
        long stopTime = System.currentTimeMillis();
        System.out.println("\nTotal Time(Writing Files and Processing): "+(stopTime-startTime)/1000+" seconds");
    }
    
    public void addFlightsToMaps(String file, HashMap<String, ArrayList> flightMap){
        System.out.println("\nStart of Processing\n");
        System.out.println("\nLoading..\n");
        int noRep=0,Rep=0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            br.readLine();
            //for(int i=0;i<100&&((line = br.readLine()) != null);i++){
            while((line = br.readLine()) != null){
                String[] a = line.split(Splitter);
                if(!"".equals(a[11])){
                    String flightName = a[4]+a[5];
                    String details = a[1]+","+a[2]+","+a[7]+","+a[8]+","+a[9]+","+a[10]+","+a[11]+","+a[12]+","+a[13]+","+a[14];
                    ArrayList<String> detailsList = new ArrayList();
                    if(flightMap.containsKey(flightName)){
                        detailsList=flightMap.get(flightName);
                        detailsList.add(details);
                        flightMap.put(flightName, detailsList);
                        Rep++;
                    }
                    else{
                        detailsList.add(details);
                        flightMap.put(flightName, detailsList);
                        Rep++;
                        noRep++;
                    }
                }
            }
            System.out.println("\nNumber of Flights with repitition: "+Rep+"\tNumber of Flights without repitition: "+noRep+"\nNumber of Files to write: "+noRep+"\n");
        }
        catch (IOException e){
        }
    }
    
    public void writeFlight(String directory,HashMap<String, ArrayList> flightMap) throws FileNotFoundException{
        System.out.println("\nWriting files..\n");
        for(String flight : flightMap.keySet()){
            ArrayList<String> array = flightMap.get(flight);
            try (PrintWriter pw = new PrintWriter(new File(directory+flight.substring(0,2)+"-"+flight.substring(2)+".csv"))) {
                for(String lines : array){
                    String sb = lines+"\n";
                    pw.write(sb);
                }
            }
        }
    }
}