/**
 * Models a shipment in the simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class Shipment {
    
    private int id, size;
    private Warehouse origin, destination;
    
    /**
     * Creates a new shipment with the given {@code id}, {@code origin}, {@code destination}, and {@code size}.
     * 
     * @param id the shipment id
     * @param origin the origin warehouse of the shipment
     * @param destination the destination warehouse of the shipment
     * @param size how large (1-3 units) the shipment is
     */
    public Shipment(int id, Warehouse origin, Warehouse destination, int size) {
        
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.size = size;
        
    }
    
    /**
     * Gets the shipment's ID.
     * 
     * @return the shipment's ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the shipment's size.
     * 
     * @return the shipment's size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets the shipment's origin warehouse.
     * 
     * @return the shipment's origin
     */
    public Warehouse getOrigin() {
        return origin;
    }
    
    /**
     * Gets the shipment's destination warehouse.
     * 
     * @return the shipment's destination
     */
    public Warehouse getDestination() {
        return destination;
    }
    
    /**
     * Gets the distance from the shipment's origin to the given position.
     * 
     * @param position the position to measure the distance to
     * @return the integer distance between the shipment's origin and the given position
     */
    public int distanceToOrigin(Point point) {
        return this.origin.getPosition().distanceTo(point);
    }
    
    /**
     * Gets the distance from the shipment's destination to the given position.
     * 
     * @param position the position to measure the distance to
     * @return the integer distance between the shipment's origin and the given position
     */
    public int distanceToDestination(Point point) {
        return this.destination.getPosition().distanceTo(point);
    }
}