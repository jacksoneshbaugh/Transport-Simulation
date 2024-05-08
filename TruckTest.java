import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Test class for {@link Truck}.
 *
 * @author Jackson Eshbaugh
 * @version 05/03/2024
 */
public class TruckTest {
    /**
     * Constructeur de la classe-test TruckTest
     */
    public TruckTest() { }
    
    /**
     * Tests the {@link Truck#load(Shipment)} method.
     * 
     * Loads shipments into a Truck, then ensures that all shipments were loaded correctly. Also verifies that
     * {@link TruckFullException} is thrown for any load that would go over the space constraints of the truck.
     */
    @Test
    @DisplayName("load(Shipment)")
    public void testLoad() {
        
        Truck truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        Shipment one = new Shipment(0, null, null, 1);
        Shipment two = new Shipment(1, null, null, 1);
        Shipment three = new Shipment(2, null, null, 1);
        
        truck.load(one);
        truck.load(two);
        truck.load(three);
        
        assertEquals(3, truck.getHoldSize());
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        one = new Shipment(0, null, null, 1);
        two = new Shipment(1, null, null, 2);
        
        truck.load(one);
        truck.load(two);
        
        assertEquals(3, truck.getHoldSize());
        
        // Exception Tests
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        one = new Shipment(0, null, null, 1);
        two = new Shipment(1, null, null, 2);
        three = new Shipment(2, null, null, 1);
        
        truck.load(one);
        truck.load(two);
        
        assertEquals(3, truck.getHoldSize());
        
        boolean flag = false;
        
        try {
            truck.load(three);
        } catch (TruckFullException e) {
            flag = true;
        }
        
        assertTrue(flag);
        assertEquals(3, truck.getHoldSize());
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        one = new Shipment(0, null, null, 2);
        two = new Shipment(1, null, null, 2);
        
        truck.load(one);
        
        assertEquals(2, truck.getHoldSize());
        
        flag = false;
        
        try {
            truck.load(two);
        } catch (TruckFullException e) {
            flag = true;
        }
        
        assertTrue(flag);
        assertEquals(2, truck.getHoldSize());
        
    }
    
    /**
     * Tests the {@link Truck#unload()} method.
     * 
     * Unloads shipments from a truck, ensuring that the correct shipments are unloaded. Also verifies that
     * {@link TruckEmptyException} is thrown when trying to unload an empty truck.
     */
    @Test
    @DisplayName("unload()")
    public void testUnload() {
        
        Truck truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        Shipment one = new Shipment(0, null, null, 1);
        Shipment two = new Shipment(1, null, null, 1);
        Shipment three = new Shipment(2, null, null, 1);
        
        truck.load(one);
        truck.load(two);
        truck.load(three);
        
        assertEquals(3, truck.getHoldSize());
        
        assertEquals(three, truck.unload());
        assertEquals(two, truck.unload());
        assertEquals(one, truck.unload());
        
        assertEquals(0, truck.getHoldSize());
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        one = new Shipment(0, null, null, 1);
        two = new Shipment(1, null, null, 2);
        
        truck.load(one);
        truck.load(two);
        
        assertEquals(3, truck.getHoldSize());
        
        assertEquals(two, truck.unload());
        assertEquals(one, truck.unload());
        
        assertEquals(0, truck.getHoldSize());
        
        // Exception Tests
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        assertEquals(0, truck.getHoldSize());
        
        one = new Shipment(0, null, null, 1);
        two = new Shipment(1, null, null, 2);
        three = new Shipment(2, null, null, 1);
        
        truck.load(one);
        truck.load(two);
        
        assertEquals(3, truck.getHoldSize());
        
        boolean flag = false;
        
        truck = new Truck(0, new Point(0, 0), 3);
        
        try {
            truck.unload();
        } catch (TruckEmptyException e) {
            flag = true;
        }
        
        assertTrue(flag);
        assertEquals(0, truck.getHoldSize());
        
    }
    
    /**
     * Tests the {@link Truck#setDestination()} method.
     * 
     * Sets up four scenarios, one where pickup is closer, one where dropoff is closer, and two where
     * both are equidistant. Ensures that the destination of the truck is set accurately.
     */
    @Test
    @DisplayName("setDestination()")
    public void testSetDestination() {
        
        // Scenario 1 (Pickup closer)
        
        Truck truck = new Truck(0, new Point(0, 0), 3);
        Warehouse w1 = new Warehouse(0, new Point(1, 1), 2);
        Warehouse w2 = new Warehouse(1, new Point(3, 4), 2);
        Warehouse w3 = new Warehouse(1, new Point(5, 4), 2);
        
        Manifest m = new Manifest();
        m.add(new Shipment(1, w1, w3, 1));
        truck.setManifest(m);
        truck.load(new Shipment(0, w3, w2, 1));
        
        truck.setDestination();
        
        assertEquals(new Point(1, 1), truck.getDestination().getPosition());
        
        // Scenario 2 (Dropoff closer)
        
        truck = new Truck(0, new Point(0, 0), 3);
        w1 = new Warehouse(0, new Point(1, 1), 2);
        w2 = new Warehouse(1, new Point(3, 4), 2);
        w3 = new Warehouse(1, new Point(5, 4), 2);
        
        m = new Manifest();
        m.add(new Shipment(1, w2, w3, 1));
        truck.setManifest(m);
        truck.load(new Shipment(0, w3, w1, 1));
        
        truck.setDestination();
        
        assertEquals(new Point(1, 1), truck.getDestination().getPosition());
        
        // Scenario 3 (Equidistant)
        
        truck = new Truck(0, new Point(1, 0), 3);
        w1 = new Warehouse(0, new Point(0, 0), 2);
        w2 = new Warehouse(1, new Point(2, 0), 2);
        w3 = new Warehouse(1, new Point(5, 4), 2);
        
        m = new Manifest();
        m.add(new Shipment(1, w2, w3, 1));
        truck.setManifest(m);
        truck.load(new Shipment(0, w3, w1, 1));
        
        truck.setDestination();
        
        assertEquals(new Point(2, 0), truck.getDestination().getPosition());
        
        // Scenario 4 (Equidistant)
        
        truck = new Truck(0, new Point(1, 0), 3);
        w1 = new Warehouse(0, new Point(0, 0), 2);
        w2 = new Warehouse(1, new Point(2, 0), 2);
        w3 = new Warehouse(1, new Point(5, 4), 2);
        
        m = new Manifest();
        m.add(new Shipment(0, w2, w3, 1));
        truck.setManifest(m);
        truck.load(new Shipment(1, w3, w1, 1));
        
        truck.setDestination();
        
        assertEquals(new Point(0, 0), truck.getDestination().getPosition());
    }
    
    /**
     * Tests the {@link Truck#move()} method.
     * 
     * Using four different scenarios with varying expected outcomes, test that a truck always moves
     * toward its destination, doesn't overshoot its destination, and takes the fastest route to the destination.
     */
    @Test
    @DisplayName("move()")
    public void testMove() {
        
        // Scenario 1: Simple move
        
        Truck truck = new Truck(0, new Point(0, 0), 5);
        truck.load(new Shipment(0, null, new Warehouse(0, new Point(1, 1), 2), 1));
        truck.setDestination();
        
        truck.move();
        
        assertEquals(new Point(1, 1), truck.getPosition());
        
        // Scenario 2: Already at destination
        
        truck = new Truck(0, new Point(0, 0), 5);
        truck.load(new Shipment(0, null, new Warehouse(0, new Point(0, 0), 2), 1));
        truck.setDestination();
        
        truck.move();
        
        assertEquals(new Point(0, 0), truck.getPosition());
        
        // Scenario 3: Capability of overshooting destination
        
        truck = new Truck(0, new Point(0, 0), 3);
        truck.load(new Shipment(0, null, new Warehouse(0, new Point(1, 1), 2), 1));
        truck.setDestination();
        
        truck.move();
        
        assertEquals(new Point(1, 1), truck.getPosition());
        
        // Scenario 4: Can't reach destination in this move.
        
        truck = new Truck(0, new Point(0, 0), 4);
        truck.load(new Shipment(0, null, new Warehouse(0, new Point(4, 4), 2), 1));
        truck.setDestination();
        
        truck.move();
        
        assertEquals(new Point(1, 1), truck.getPosition());
        
    }
    
    /**
     * Tests the {@link Truck#action()} method.
     * Uses a sample scenario over two simulation hours where a truck arrives at the warehouse, then
     * joins the queue.
     */
    @Test
    @DisplayName("action()")
    public void testAction() {
        
        Warehouse w = new Warehouse(0, new Point(1, 1), 2);
        
        Truck truck = new Truck(0, new Point(0, 0), 5);
        truck.load(new Shipment(0, null, w, 1));
        
        truck.setDestination();
        
        truck.action();
        
        assertEquals(new Point(1, 1), truck.getPosition());
        
        truck.action();
        
        assertEquals(new Point(1, 1), truck.getPosition());
        assertEquals(1, w.getNumQueuingTrucks());
        
    }
}
