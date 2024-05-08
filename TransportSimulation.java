import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class controls a simulation of trucks delivering and picking up shipments from warehouses.
 * 
 * @author Jackson Eshbaugh
 * @version 05/04/2024
 */
public class TransportSimulation {
    
    private int time, maxX, maxY, numTrucks, numWarehouses, minShipments, maxShipments;
    private ItemList<Truck> trucks; 
    private ItemList<Warehouse> warehouses;
    
    /**
     * Creates a new TransportSimulation, randomly generating trucks, warehouses, and shipments.
     * 
     * @param maxX the maximum x that the simulation extends to
     * @param maxY the maximum y that the simulation extends to
     * @param numTrucks the number of trucks in the simulation
     * @param numWarehouses the number of warehouses in the simulation
     * @param numShipments the number of shipments per truck
     */
    public TransportSimulation(int maxX, int maxY, int numTrucks, int numWarehouses, int minShipments, int maxShipments) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.numTrucks = numTrucks;
        this.numWarehouses = numWarehouses;
        this.minShipments = minShipments;
        this.maxShipments = maxShipments;
        
        this.trucks = new ItemList<Truck>();
        this.warehouses = new ItemList<Warehouse>();
        
        for(int i = 0; i < numTrucks; ++i) {
            int load = 2 + (int)(Math.random() * ((5 - 2) + 1));
            int xVal = 0 + (int)(Math.random() * ((maxX - 0) + 1));
            int yVal = 0 + (int)(Math.random() * ((maxY - 0) + 1));
            
            trucks.add(new Truck(i, new Point(xVal, yVal), load));
        }
        
        for(int i = 0; i < numWarehouses; ++i) {
            int docks = 1 + (int)(Math.random() * ((3 - 1) + 1));
            int xVal = 0, yVal = 0;
            
            // Two warehouses can't have the same coordinates.
            boolean flag = false;
            while(!flag) {
                flag = true;
                
                xVal = 0 + (int)(Math.random() * ((maxX - 0) + 1));
                yVal = 0 + (int)(Math.random() * ((maxY - 0) + 1));
                
                for(int j = 0; i < warehouses.size(); ++i) {
                    if(warehouses.get(j).getPosition().equals(new Point(xVal, yVal))) {
                        flag = false;
                        break;
                    }
                }
            }
            
            warehouses.add(new Warehouse(i, new Point(xVal, yVal), docks));
        }
        
        int idCounter = 0;
        
        for(int i = 0; i < trucks.size(); ++i) {
            Manifest m = new Manifest();
            
            int numShipments = minShipments + (int)(Math.random() * ((maxShipments - minShipments) + 1));
            
            for(int j = 0; j < numShipments; ++j) {
                int id = idCounter + j;
                int size = 1 + (int)(Math.random() * ((Math.min(trucks.get(i).getHoldSizeLimit(), 3) - 1) + 1));
                Warehouse origin = warehouses.get(0 + (int)(Math.random() * (((warehouses.size() - 1) - 0) + 1)));
                Warehouse destination = warehouses.get(0 + (int)(Math.random() * (((warehouses.size() - 1) - 0) + 1)));
                
                while(origin.getId() == destination.getId()) {
                    destination = warehouses.get(0 + (int)(Math.random() * (((warehouses.size() - 1) - 0) + 1)));
                }
                
                m.add(new Shipment(id, origin, destination, size));
            }
            idCounter += numShipments;
            trucks.get(i).setManifest(m);
            trucks.get(i).setDestination();
        }
    }
    
    /**
     * Creates a new TransportSimulation, allowing the manual addition of trucks & warehouses.
     * 
     * @param maxX the maximum x that the simulation extends to
     * @param maxY the maximum y that the simulation extends to
     */
    public TransportSimulation(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }
    
    /**
     * Adds a truck to the simulation. Before adding, trucks should have their manifests assigned.
     * 
     * @param truck the truck to add to the simulation
     */
    public void addTruck(Truck truck) {
        trucks.add(truck);
    }
    
    /**
     * Adds a warehouse to the simulation.
     * 
     * @param warehouse the warehouse to add to the simulation
     */
    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }
    
    /**
     * Runs the simulation for a given number of hours.
     * 
     * @param hours the number of hours to run the simulation for
     */
    public void run(int hours) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(References.LOG_FILE_NAME, true))){
            w.write("*** Transportation Simulation *** (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ")");
            w.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
        for(int i = 1; i <= hours; ++i) {
            System.out.println("Hour " + i);
            try (BufferedWriter w = new BufferedWriter(new FileWriter(References.LOG_FILE_NAME, true))){
                w.write("Hour " + i);
                w.newLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            
            for(int j = 0; j < trucks.size(); ++j) {
                trucks.get(j).action();
                trucks.get(j).log();
            }
            
            for(int j = 0; j < warehouses.size(); ++j) {
                warehouses.get(j).action();
            }
        }
    }
}