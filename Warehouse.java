import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

/**
 * Represents a warehouse in the simulation. Trucks unload and load shipments here.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class Warehouse implements Schedule {

    private Point position;
    private int id;
    private ItemList<Dock> docks;
    private MyQueue<Truck> truckQueue;
    private String actionDescription = "";

    /**
     * Creates a new Warehouse at the position given by the point with the given number of docks.
     * 
     * @param id the warehouse's id
     * @param point the warehouse's location
     * @param docks the number of docks
     */
    public Warehouse(int id, Point point, int docks) {
        this.position = point;
        this.id = id;
        this.docks = new ItemList<Dock>();
        this.truckQueue = new MyQueue<Truck>();

        // TODO: Create Docks
        for (int i = 0; i < docks; ++i) {
            this.docks.add(new Dock(this.id, this.position));
        }
    }

    /**
     * Fetches the warehouse's ID.
     * 
     * @return the warehouse's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Adds the given {@code Truck} to the queue of trucks waiting to dock at the warehouse.
     * 
     * @param truck the truck to add to the queue
     */
    public void joinQueue(Truck truck) {
        truckQueue.add(truck);
    }

    /**
     * Gets the number of docks at this warehouse.
     * 
     * @return the number of docks
     */
    public int getNumDocks() {
        return docks.size();
    }

    /**
     * Gets the number of docks with a truck docked at this warehouse.
     * 
     * @return the number of occupied docks
     */
    public int getNumOccupiedDocks() {
        int count = 0;
        for(int i = 0; i < docks.size(); ++i) {
            if(docks.get(i).isOccupied()) count++;
        }

        return count;
    }

    /**
     * Gets the warehouse's poistion.
     * 
     * @return the warehouse's position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the number of trucks currently queuing.
     * 
     * @return the number of trucks currently in the queue
     */
    public int getNumQueuingTrucks() {
        return truckQueue.size();
    }

    /**
     * Gets the list of docks (primarily for unit testing)
     * 
     * @return the list of docks
     */
    public ItemList<Dock> getDocks() {
        return docks;
    }

    @Override
    public void action() {
        for(int i = 0; i < docks.size(); ++i) {
            Dock d = docks.get(i);
            if(!d.isOccupied() && !truckQueue.isEmpty()) {
                d.dock(truckQueue.poll());
            }
            d.handle();
            actionDescription = d.actionString(); // TODO: Fix blank line errors coming from here
            if(!actionDescription.equals("")) log();
        }
    }

    @Override
    public void log() {
        System.out.println(actionDescription);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(References.LOG_FILE_NAME, true))){
            w.write(actionDescription);
            w.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        actionDescription = "";
    }

    /**
     * Models a loading/unloading dock at a warehouse.
     */
    public static class Dock {
        private boolean occupied;
        private Truck truck;
        private int warehouseId;
        private Point warehousePos;
        private String actionDescription = "";

        /**
         * Creates a new dock.
         * 
         * @param warehouseId the id of the warehouse where this dock is located
         */
        public Dock(int warehouseId, Point pos) {
            this.warehouseId = warehouseId;
            this.warehousePos = pos;
        }

        /**
         * Docks a truck at this dock.
         * 
         * @param truck the truck to dock at this dock
         * @return {@code true} if the docking was successful
         */
        public boolean dock(Truck truck) {
            if(occupied) return false;
            this.truck = truck;
            occupied = true;
            return true;
        }

        /**
         * Removes the truck from this dock.
         */
        public void remove() {
            truck = null;
            occupied = false;
        }

        /**
         * Handles the dock (called once every hour by {@code action()} above.
         */
        public void handle() {
            if(!isOccupied()) return;

            if(truck.getHoldSize() > 0 && truck.peekHold().getDestination().getId() == warehouseId) {
                // truck has to unload here.
                Shipment s = truck.unload();
                actionDescription = "Truck " + getTruck().getId() + ": " + "Unloaded shipment id " + s.getId();
                if(getTruck().getDestination() == null) {
                    actionDescription += " [Destination: no destination; Cargo Hold: (" + getTruck().getHoldSize() + "/" + getTruck().getHoldSizeLimit() + ")]";
                } else {
                    actionDescription += " [Destination: (" + getTruck().getDestination().getPosition().x
                    + ", " + getTruck().getDestination().getPosition().y + "); Cargo Hold: (" + getTruck().getHoldSize() + "/" + getTruck().getHoldSizeLimit() + ")]";
                }
            } else if(truck.getHoldSize() < truck.getHoldSizeLimit() && truck.getManifest().nextPickUp(warehousePos) != null
            && truck.getManifest().nextPickUp(warehousePos).getOrigin().getId() == warehouseId && truck.getManifest().nextPickUp(warehousePos).getSize()
            + truck.getHoldSize() <= truck.getHoldSizeLimit()) {
                // truck has to pick up here.
                Shipment s = truck.getManifest().pickUp(warehousePos);
                truck.load(s);
                actionDescription = "Truck " + getTruck().getId() + ": " + "Loaded shipment id " + s.getId();
                if(getTruck().getDestination() == null) {
                    actionDescription += " [Destination: no destination; Cargo Hold: (" + getTruck().getHoldSize() + "/" + getTruck().getHoldSizeLimit() + ")]";
                } else {
                    actionDescription += " [Destination: (" + getTruck().getDestination().getPosition().x
                    + ", " + getTruck().getDestination().getPosition().y + "); Cargo Hold: (" + getTruck().getHoldSize() + "/" + getTruck().getHoldSizeLimit() + ")]";
                }
            }

            truck.setDestination();
            
            if(truck.getDestination() == null || truck.getDestination().getId() != warehouseId) {
                truck.doneQueuing();
                remove();
            }
        }

        /**
         * Determines whether the dock is occupied or not.
         * 
         * @return {@code true} if the dock is occupied, {@code false} otherwise
         */
        public boolean isOccupied() {
            return occupied;
        }

        /**
         * Gets the truck at the dock.
         * 
         * @return the truck at the dock
         */
        public Truck getTruck() {
            return truck;
        }

        /**
         * Returns the action String, for use in logging. Resets the String after returning it.
         * 
         * @return the action String
         */
        public String actionString() {
            String ret = actionDescription;
            actionDescription = "";
            return ret;
        }
    }
}