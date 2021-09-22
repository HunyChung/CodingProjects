// --== CS400 File Header Information ==--
// Name: Sanghun Chung
// Email: schung72@wisc.edu
// Team: Blue
// Role: Data Wrangler
// TA: TA Mohan
// Lecturer: Florian


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;



public class BusRouteDataReader implements BusRouteDataReaderInterface{

  //THIS IS THE DATA READER SO I READ THE DATA AND I SAVE THESE VALUES TO BE OUTPUT
  public BusRouteDataReader() throws IOException, DataFormatException{
    //BusData Reader Object
    Reader dataFile = new FileReader("C:\\\\Users\\\\Huny\\\\eclipse-workspace\\\\project3\\\\src\\\\BusRouteData.txt");    
    readDataSet(dataFile);
    
  }
  
  
  public class BusRouteData implements BusRouteDataInterface{
    
    Set<BusStopInterface> BusStops = new HashSet<BusStopInterface>();
    Set<BusRouteInterface> BusRoutes = new HashSet<BusRouteInterface>();
    Map<String,BusInterface> BusNamesMap = new HashMap<String,BusInterface>();
    Map<String, BusStopInterface> BusStopNamesMap = new HashMap<String, BusStopInterface>();
    
    /**
     * Gets a set of ALL bus stops that will be in the graph
     * IMPORTANT these are the graph VERTICES
     * @return set of ALL bus stops that will be in the graph
     */
    public Set<BusStopInterface> getBusStops(){
      return BusStops;
      
    }
    

    /**
     * Gets a set of ALL bus routes that will be in the graph
     * IMPORTANT these are the graph EDGES
     * @return set of ALL bus routes that will be in the graph
     */
    public Set<BusRouteInterface> getBusRoutes(){
      return BusRoutes;
      
    }
    
    /**
     * Map of bus route numbers to bus objects
     * Ex) calling map.get("80") will return the bus object that follows route 80
     * @return map of bus route numbers to bus objects
     */
    public Map<String, BusInterface> getBusNamesMap(){
      return BusNamesMap;
      
    }
   
    /**
     * Map of bus stop names to bus stop objects
     * Ex) calling map.get("Langdon & N Park") will return the bus stop object at that street address
     * @return map of bus stop names to bus stop objects
     */
    public Map<String, BusStopInterface> getBusStopNamesMap(){
      return BusStopNamesMap;
      
    }
    
    
  }
  
  
  
  //THIS IS THE VERTEX
  public class BusStop implements BusStopInterface {

    private String name;
    private String routeNumber;
    private Set<BusInterface> buses = new HashSet<BusInterface>();
    
    /**
     * New bus stop
     * @param name = name of the bus stop
     * @param routeNumber = the route number this bus stop is on
     * @param buses 
     * @return name of the bus
     */
    public BusStop(String name, String routeNumber, BusInterface bus) {
        this.name = name;
        this.routeNumber = routeNumber;
        buses.add(bus);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRouteNumber() {
        return this.routeNumber;
    }

    @Override
    public Set<BusInterface> getBuses() {
        return this.buses;
    }
}
  
  public class Bus implements BusInterface {

    private String routeNumber;
    private List<BusStopInterface> AllStops = new ArrayList<BusStopInterface>();
    
    public Bus(String routeNum) {
      this.routeNumber = routeNum;
    }

    @Override
    public String getRouteNumber() {
      return routeNumber;
    }

    @Override
    public String getName() {
//      System.out.println("This is the route Number"+routeNumber);
      if(Integer.parseInt(routeNumber) == 80) {
        return "Memorial Union // Eagle Heights";
      }
      if(Integer.parseInt(routeNumber) == 81) {
        return "Park // Broom // Johnson/Gorham";
      }
      if(Integer.parseInt(routeNumber) == 82) {
        return "Observatory // Breese Loop";
      }
      if(Integer.parseInt(routeNumber) == 84) {
        return "Eagle Heights Loop";
      }
      return "no name found!";
    }

    @Override
    public List<BusStopInterface> getStops() {
      return AllStops;
    }
  }
  
  //THIS IS THE EDGE
  public class BusRoute implements BusRouteInterface {

    private BusStopInterface start;
    private BusStopInterface destination;
    private int time;
    
    public BusRoute(BusStopInterface start, BusStopInterface destination, int distance) {
      this.start = start;
      this.destination = destination;
      this.time = distance;
  }
    
    @Override
    public BusStopInterface getStart() {
        return this.start;
    }

    @Override
    public BusStopInterface getDestination() {
        return this.destination;
    }

    @Override
    public int getTime() {
        return this.time;
    }
  }
  
  
  
  public BusRouteDataInterface readDataSet(Reader dataFile)
      throws IOException, DataFormatException {
    
    BusRouteData BRD = new BusRouteData();
    
    
    //<Key, HashTable of vertices>
    //Map<String, BusInterface> routeToVerticesMap = new HashMap<String, BusInterface>();
    
    char nextChar = (char) dataFile.read();
    StringBuilder nextLine = new StringBuilder(300);
    
    
    // Skip every character until \n comes up which signals the end of header
    while (nextChar != '\n' && nextChar != -1) {
      nextChar = (char) dataFile.read();
    }
    
    //reset the first letter
    nextChar = (char) dataFile.read();
    
    while (nextChar != -1) {
      
      // Read until the next line or end of stream is reached.
      while (nextChar != '\n' && nextChar != 65535) {
        nextLine.append(nextChar);
        nextChar = (char) dataFile.read();
      }
      nextChar = (char) dataFile.read();
      
      //If the data set was empty then return null;
      if (nextLine.length() == 0) {
        return null;
      }
      
      //Data in array form
      String[] BusData = nextLine.toString().split(",");  
      
      //So the variables I need are Route Key, Start, target, distance
      //First set the first element as Route Key variable
      String key = BusData[0];
      
      //repeater = # of sets of stops to distance
      int repeater = (BusData.length-1)/2;
      
      
      //create new object of HashTable
      Bus newBus = new Bus(key);
      
//      System.out.println(newBus.getName());
      //then repeat the process of getting stop and distance by the number of sets there are
      for (int i = 0; i < repeater; i++) {
        int stop = i*2+1;
        int distance = i*2+2;
        int target = i*2+3;
        
//        System.out.println(""+stop+","+distance+","+target);
//        System.out.println(Arrays.toString(BusData));
//        System.out.println(""+BusData[stop]+","+BusData[distance]+","+BusData[target]);
        //Create new Vertex
        //Parameters needed are name, route number, bus
        BusStop newStop = new BusStop(BusData[stop], key, newBus);
        
        BRD.BusStops.add(newStop);
        newBus.AllStops.add(newStop);
        
        
        BusStop newTarget = new BusStop(BusData[target], key, newBus);
        
        BRD.BusStops.add(newTarget);
        
        
        //if this target Stop is equal to the first stop then add the target stop as the last stop
        if((newBus.AllStops.get(0)).equals(newTarget)) {
          newBus.AllStops.add(newTarget);
        }
        
        //Create new Edge
        //Parameters needed are current, target, time 
        BusRoute newRoute = new BusRoute(newStop, newTarget, Integer.parseInt(BusData[distance]));
        BRD.BusRoutes.add(newRoute);
       
        //Map Bus Stop Name to Bus Stop Object
        BRD.BusNamesMap.put(key, newBus);
        
        //string, BusStopInterface
        BRD.BusStopNamesMap.put(BusData[stop], newStop);
        
      }

      nextLine = new StringBuilder(300);
       
    }
    return BRD;
  }
  

}
