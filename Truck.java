import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

/**
 * Representation of a truck in the simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class Truck implements Schedule {

    private Point position;
    private int id, loadLimit, speed;
    private ArrayBasedStack<Shipment> hold;
    private int holdSize = 0;
    private Manifest manifest;
    private Warehouse destination;
    private boolean queuing = false;
    private String actionDescription = "";

    /**
     * Creates a new truck with {@code loadLimit} at position given by {@code point}, setting
     * speed based on {@code loadLimit}.
     * 
     * @param id the truck's id
     * @param point the starting position of the truck
     * @param loadLimit how large the truck's hold should be
     */
    public Truck(int id, Point point, int loadLimit) {
        this.manifest = new Manifest();
        this.loadLimit = loadLimit;
        this.position = point;
        this.id = id;
        this.speed = 6 - loadLimit;
        this.hold = new ArrayBasedStack<Shipment>();
    }

    /**
     * Fetches the truck's id.
     * 
     * @return the truck's id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the truck's manifest.
     * 
     * @param manifest the manifest for the truck
     */
    public void setManifest(Manifest manifest) {
        this.manifest = manifest;
        setDestination();
    }

    /**
     * Fetches the truck's manifest.
     * 
     * @return the truck's manifest
     */
    public Manifest getManifest() {
        return manifest;
    }

    /**
     * Loads a shipment onto the truck.
     * 
     * @throws TruckFullException if the truck cannot accept the cargo entirely
     * @param shipment the shipment to load onto the truck
     */
    public void load(Shipment shipment) {
        if(holdSize + shipment.getSize() > loadLimit) throw new TruckFullException("Not enough room on truck to fit shipment.");

        holdSize += shipment.getSize();
        hold.push(shipment);
    }

    /**
     * Offloads the last loaded shipment from the truck.
     * @throws TruckEmptyException if the truck is empty
     * 
     * @return the {@code Shipment} unloaded from the truck
     */
    public Shipment unload() {
        if(holdSize == 0) throw new TruckEmptyException("There's nothing in the hold of the truck!");

        Shipment ret = hold.pop();
        holdSize -= ret.getSize();

        return ret;
    }

    /**
     * Returns the next-to-be-unloaded shipment from the truck.
     * @throws TruckEmptyException if the truck is empty
     * 
     * @return the {@code Shipment} next to be unloaded
     */
    public Shipment peekHold() {
        if(holdSize == 0) throw new TruckEmptyException("There's nothing in the hold of the truck!");

        return hold.peek();
    }

    /**
     * Fetches the number of shipments currently in the hold.
     * 
     * @return the number of shipments currently in the truck's hold
     */
    public int getHoldSize() {
        return holdSize;
    }

    /**
     * Fetches hold size limit for the truck.
     * 
     * @return the hold size limit for the truck
     */
    public int getHoldSizeLimit() {
        return loadLimit;
    }

    /**
     * Sets the truck's destination.
     */
    public void setDestination() {
        Shipment nextPickUp = manifest.nextPickUp(this.position);
        Shipment nextDropOff = null;

        if(getHoldSize() > 0) nextDropOff = hold.peek();

        if(nextPickUp == null && nextDropOff == null) {
            destination = null;
        } else if(nextPickUp == null)  {
            destination = nextDropOff.getDestination();
        } else if(nextDropOff == null) {
            destination = nextPickUp.getOrigin();
        } else if(nextPickUp.distanceToOrigin(position) < nextDropOff.distanceToDestination(position) && nextPickUp.getSize() <= loadLimit - holdSize) {
            destination = nextPickUp.getOrigin();
        } else if(nextPickUp.distanceToOrigin(position) > nextDropOff.distanceToDestination(position)) {
            destination = nextDropOff.getDestination();
        } else {
            // if the distances are equal, newer shipment is given priority.
            if(nextPickUp.getId() > nextDropOff.getId() && nextPickUp.getSize() <= loadLimit - holdSize) {
                destination = nextPickUp.getOrigin();
            } else {
                destination = nextDropOff.getDestination();
            }
        }
    }

    /**
     * Moves the truck as close as possible to the destination given its speed.
     */
    public void move() {

        if(destination == null) {
            actionDescription = "[Manifest Empty]";
            return;
        }

        int destinationDistance = getPosition().distanceTo(destination.getPosition());

        if(destinationDistance == 0) {
            actionDescription = "remaining at (" + getPosition().x + ", " + getPosition().y + ")";
        } else if(destinationDistance <= speed) {
            actionDescription = "(" + getPosition().x + ", " + getPosition().y + ") -> (" + 
            destination.getPosition().x + ", " + destination.getPosition().y + ")";
            this.position = destination.getPosition();
        } else {
            // Get as close as we can using similar triangles

            actionDescription = "(" + getPosition().x + ", " + getPosition().y + ") -> (";
            double ratio = (double) speed / (double) destinationDistance;
            int newX = (int) Math.round(position.x + (destination.getPosition().x - position.x) * ratio);
            int newY = (int) Math.round(position.y + (destination.getPosition().y - position.y) * ratio);

            this.position = new Point(newX, newY);
            actionDescription += getPosition().x + ", " + getPosition().y + ")";
        }
    }

    /**
     * Fetches the truck's destination.
     * 
     * @return the destination warehouse of the truck
     */
    public Warehouse getDestination() {
        return destination;
    }

    /**
     * Fetches the truck's position.
     * 
     * @return the position of the truck.
     */
    public Point getPosition() {
        return position;
    }
    
    /**
     * Resets the truck's queuing state.
     */
    public void doneQueuing() {
        queuing = false;
    }

    @Override
    public void action() {
        move();
        if(destination != null && !queuing && getPosition().distanceTo(destination.getPosition()) == 0) {
            destination.joinQueue(this);
            queuing = true;
        }
    }

    @Override
    public void log() {
        String log = "Truck " + id + ": " + actionDescription;

        if(destination == null) {
            log += " [Destination: no destination; Cargo Hold: (" + holdSize + "/" + loadLimit + ")]";
        } else {
            log += " [Destination: (" + destination.getPosition().x
            + ", " + destination.getPosition().y + "); Cargo Hold: (" + holdSize + "/" + loadLimit + ")]";
        }

        System.out.println(log);

        try (BufferedWriter w = new BufferedWriter(new FileWriter(References.LOG_FILE_NAME, true))){
            w.write(log);
            w.newLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        actionDescription = "";
    }
}