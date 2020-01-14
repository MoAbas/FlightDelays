package flightreport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class T1{
    
    static String Splitter = ",";
    static String line;
    
    public static void main(String[] args){
        T1 o = new T1();
        HashMap<String,String> airportMap = new HashMap();
        HashMap<String,ArrayList> delayMap = new HashMap();
        TreeMap<String,Integer> countMap = new TreeMap();
        String flights = "flights.csv";
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter starting Day: ");
        String day1= sc.next();
        System.out.print("Enter starting Month: ");
        String month1= sc.next();
        System.out.print("\nEnter finishing Day: ");
        String day2= sc.next();
        System.out.print("Enter finishing Month: ");
        String month2= sc.next();
        String sDate="";
        String fDate="";
        if(Integer.parseInt(month1)>9&&Integer.parseInt(day1)>9){
            sDate=month1+day1;
        }
        else if(Integer.parseInt(month1)<=9&&Integer.parseInt(day1)<=9){
            sDate="0"+month1+"0"+day1;
        }
        else if(Integer.parseInt(month1)<=9&&Integer.parseInt(day1)>9){
            sDate="0"+month1+day1;
        }
        else if(Integer.parseInt(month1)>9&&Integer.parseInt(day1)<=9){
            sDate=month1+"0"+day1;
        }
        if(Integer.parseInt(month2)>9&&Integer.parseInt(day2)>9){
            fDate=month2+day2;
        }
        else if(Integer.parseInt(month2)<=9&&Integer.parseInt(day2)<=9){
            fDate="0"+month2+"0"+day2;
        }
        else if(Integer.parseInt(month2)<=9&&Integer.parseInt(day2)>9){
            fDate="0"+month2+day2;
        }
        else if(Integer.parseInt(month2)>9&&Integer.parseInt(day2)<=9){
            fDate=month2+"0"+day2;
        }
        System.out.println("\nChosen Date Interval: "+sDate.substring(2)+"/"+sDate.substring(0,2) +"  &  "+fDate.substring(2)+"/"+fDate.substring(0,2));
        long startTime = System.currentTimeMillis();
        long startTimeProcessing = System.currentTimeMillis();
        o.addFlightsToMaps(flights, sDate, fDate, month2, airportMap, delayMap);
        o.assignCount(countMap, delayMap);
        Map<String, Integer> sortedByCount = o.sortByValue(countMap);
        long stopTimeProcessing = System.currentTimeMillis();
        o.printFlight(sortedByCount,delayMap,airportMap);
        long stopTime = System.currentTimeMillis();
        System.out.println("\nProcessing Time: "+(stopTimeProcessing-startTimeProcessing)/1000+" seconds");
        System.out.println("\nTotal Time(Processing and Printing): "+(stopTime-startTime)/1000+" seconds");
    }
    
    public void addFlightsToMaps(String file, String sDate, String fDate, String fMonth, HashMap<String,String> airportMap, HashMap<String,ArrayList> delayMap) {
        System.out.println("\nStart of Processing\n");
        System.out.println("\nLoading..\n");
        int noRep=0,Rep=0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            br.readLine();
            //for(int i=0;i<100000&&((line = br.readLine()) != null);i++){
            while((line = br.readLine()) != null && Integer.parseInt(line.split(Splitter)[1])<=Integer.parseInt(fMonth)){
                
                String[] a = line.split(Splitter);
                if(!"".equals(a[11])){
                    String month = a[1];
                    String day = a[2];
                    String cDate = "";
                    String flightName = a[4]+a[5];
                    int delay = Integer.parseInt(a[11]);
                    String airport = a[7]+"-"+a[8];
                    ArrayList<Integer> delayList = new ArrayList();
                    if(Integer.parseInt(month)>9&&Integer.parseInt(day)>9){
                        cDate=month+day;
                    }
                    else if(Integer.parseInt(month)<=9&&Integer.parseInt(day)<=9){
                        cDate="0"+month+"0"+day;
                    }
                    else if(Integer.parseInt(month)<=9&&Integer.parseInt(day)>9){
                        cDate="0"+month+day;
                    }
                    else if(Integer.parseInt(month)>9&&Integer.parseInt(day)<=9){
                        cDate=month+"0"+day;
                    }
                    if((Integer.parseInt(cDate)>=Integer.parseInt(sDate))&&(Integer.parseInt(cDate)<=Integer.parseInt(fDate))){
                        if(delayMap.containsKey(flightName)){
                            delayList=delayMap.get(flightName);
                            delayList.add(delay);
                            delayMap.put(flightName, delayList);
                            Rep++;
                        }
                        else{
                            delayList.add(delay);
                            airportMap.put(flightName, airport);
                            delayMap.put(flightName, delayList);
                            Rep++;
                            noRep++;
                        }
                    }
                }
            }
            System.out.println("\nNumber of Flights with repitition: "+Rep+"\tNumber of Flights without repitition: "+noRep+"\n");
        }
        catch (IOException e){
        }
    }
    
    public void assignCount(TreeMap<String,Integer> countMap, HashMap<String,ArrayList> delayMap){
        for (Map.Entry<String, ArrayList> entry : delayMap.entrySet()){
            String key = entry.getKey();
            int value = entry.getValue().size();
            countMap.put(key, value);
        }
    }
    
    public Map<String, Integer> sortByValue(TreeMap<String, Integer> countMap){
        return countMap.entrySet().stream().sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
    
    public void printFlight(Map<String, Integer> sortedByCount, HashMap<String,ArrayList> delayMap, HashMap<String,String> airportMap){
        System.out.println("Flight Number:\t\tOr-Des airports:\tEntries:\tMin Delay:\tMax Delay:\tAverage Delay:");
        for(String flight : sortedByCount.keySet()){
            ArrayList<Integer> array = delayMap.get(flight);
            String airports = airportMap.get(flight);
            int count = sortedByCount.get(flight);
            Object min = Collections.min(array);
            Object max = Collections.max(array);
            double sum = 0;
            for(Integer element : array){
                sum += element;
            }
            double average = (sum/count);
            System.out.println(flight+"\t\t\t"+airports+"\t\t\t"+count+"\t\t"+min+"\t\t"+max+"\t\t"+String.format("%.2f", average));
        }
    }
}