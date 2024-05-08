/**
 * A representation of the manifest of a truck in the simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class Manifest {
    private ItemList<Shipment> pickUps;
    
    /**
     * Creates a new manifest.
     */
    public Manifest() {
        this.pickUps = new ItemList<Shipment>();
    }
    
    /**
     * Adds a shipment to the manifest.
     * 
     * @param shipment the shipment to add to the manifest
     */
    public void add(Shipment shipment) {
        pickUps.add(shipment);
    }
    
    /**
     * Called when a truck picks up a shipment. Removes the shipment from the manifest.
     * 
     * @param position the current position of the truck
     * @return the picked up shipment
     */
    public Shipment pickUp(Point position) {
        Shipment next = pickUps.remove(nextPickUpIndex(position));
        return next;
    }
    
    /**
     * Gets the next shipment the truck should pick up, given the truck's {@code position}.
     * 
     * @param position the position of the truck
     * @return the next shipment the truck should pick up
     */
    public Shipment nextPickUp(Point position) {
        Shipment next = null;
        for(int i = 0; i < pickUps.size(); ++i) {
            if(next == null) next = pickUps.get(i);
            else if(pickUps.get(i).distanceToOrigin(position) < next.distanceToOrigin(position)) next = pickUps.get(i);
            else if(pickUps.get(i).distanceToOrigin(position) == next.distanceToOrigin(position)) {
                if(pickUps.get(i).getId() > next.getId()) next = pickUps.get(i);
            }
        }
        
        return next;
    }
    
    /**
     * Gets the index of the next shipment the truck should pick up, given the truck's {@code position}.
     * 
     * @param position the position of the truck
     * @return the index of the next shipment the truck should pick up
     */
    private int nextPickUpIndex(Point position) {
        int next = -1;
        for(int i = 0; i < pickUps.size(); ++i) {
            if(next == -1) next = i;
            else if(pickUps.get(i).distanceToOrigin(position) < pickUps.get(next).distanceToOrigin(position)) next = i;
            else if(pickUps.get(i).distanceToOrigin(position) == pickUps.get(next).distanceToOrigin(position)) {
                if(pickUps.get(i).getId() > pickUps.get(next).getId()) next = i;
            }
        }
        
        return next;
    }
    
    /**
     * Returns the number of shipments currently in the manifest.
     * 
     * @return the size of the manifest
     */
    public int size() {
        return pickUps.size();
    }
}