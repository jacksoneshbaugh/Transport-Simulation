import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Classe-test WarehouseTest.
 *
 * @author Jackson Eshbaugh
 * @version 05/01/2024
 */
public class WarehouseTest {
    
    public WarehouseTest() { }
    
    
    // Dock methods
    
    /**
     * Tests the {@link Warehouse#Dock#dock(Truck)} method. Ensures that {@code false} is returned if dock is
     * already full, otherwise {@code true}.
     */
    @Test
    @DisplayName("Dock.dock(Truck)")
    public void testDock() {
        
        Warehouse.Dock d = new Warehouse.Dock(0, new Point(0, 0));
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(new Truck(0, new Point(0, 0), 5)));
        assertTrue(d.isOccupied());
        
        assertFalse(d.dock(new Truck(1, new Point(0, 0), 5)));
        assertTrue(d.isOccupied());
    }
    
    /**
     * Tests the {@link Warehouse#Dock#remove()} method. Ensures that the dock is no longer occuped after
     * a truck is removed from the dock.
     */
    @Test
    @DisplayName("Dock.remove()")
    public void testRemove() {
        
        Warehouse.Dock d = new Warehouse.Dock(0, new Point(0, 0));
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(new Truck(0, new Point(0, 0), 5)));
        assertTrue(d.isOccupied());
        
        d.remove();
        assertFalse(d.isOccupied());
    }
    
    /**
     * Tests the {@link Warehouse#Dock#handle()} method, using four scenarios with different outcomes when
     * calling the method.
     */
    @Test
    @DisplayName("Dock.handle()")
    public void testHandle() {
        
        // Scenario 1 (Truck with no buisness at dock)
        
        Warehouse.Dock d = new Warehouse.Dock(0, new Point(0, 0));
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(new Truck(0, new Point(0, 0), 5)));
        assertTrue(d.isOccupied());
        
        d.handle();
        assertFalse(d.isOccupied());
        
        
        // Scenario 2 (Truck with a shipment to load)
        
        d = new Warehouse.Dock(0, new Point(0, 0));
        Truck t = new Truck(0, new Point(0, 0), 5);
        
        Manifest m = new Manifest();
        m.add(new Shipment(0, new Warehouse(0, new Point(0, 0), 1), new Warehouse(1, new Point(2, 4), 1), 1));
        t.setManifest(m);
        
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(t));
        assertTrue(d.isOccupied());
        
        d.handle();
        assertFalse(d.isOccupied());
        assertEquals(1, t.getHoldSize());
        
        d.handle();
        assertFalse(d.isOccupied());
        
        // Scenario 3 (Truck with a shipment to unload)
        
        d = new Warehouse.Dock(0, new Point(0, 0));
        t = new Truck(0, new Point(0, 0), 5);
        t.load(new Shipment(0, new Warehouse(1, new Point(2, 4), 1), new Warehouse(0, new Point(0, 0), 1), 1));
        
        assertEquals(1, t.getHoldSize());
        
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(t));
        assertTrue(d.isOccupied());
        
        d.handle();
        assertFalse(d.isOccupied());
        assertEquals(0, t.getHoldSize());
        
        d.handle();
        assertFalse(d.isOccupied());
        
        // Scenario 4 (Truck with both a shipment to unload and a shipment to load)
        
        d = new Warehouse.Dock(0, new Point(0, 0));
        t = new Truck(0, new Point(0, 0), 5);
        t.load(new Shipment(0, new Warehouse(1, new Point(2, 0), 1), new Warehouse(0, new Point(0, 0), 1), 1));
        m.add(new Shipment(1, new Warehouse(0, new Point(0, 0), 1), new Warehouse(1, new Point(2, 0), 1), 1));
        t.setManifest(m);
        
        assertEquals(1, t.getHoldSize());
        
        assertFalse(d.isOccupied());
        
        assertTrue(d.dock(t));
        assertTrue(d.isOccupied());
        
        d.handle();
        assertTrue(d.isOccupied());
        assertEquals(0, t.getHoldSize());
        
        d.handle();
        assertFalse(d.isOccupied());
        assertEquals(1, t.getHoldSize());
    }
    
    /**
     * Tests the {@link Warehouse#action()} method, using multiple scenarios to test the different possible
     * outcomes/functionalities of the method.
     */
    @Test
    @DisplayName("Warehouse.action()")
    public void testAction() {
        
        // 1 dock, 1 truck in queue
        
        Warehouse w = new Warehouse(0, new Point(0, 0), 1);
        Truck t = new Truck(0, new Point(0, 0), 5);
        
        w.joinQueue(t);
        assertFalse(w.getDocks().get(0).isOccupied());
        assertEquals(1, w.getNumQueuingTrucks());
        
        w.action();
        assertFalse(w.getDocks().get(0).isOccupied());
        assertEquals(0, w.getNumQueuingTrucks());
        
        // 2 docks, 2 trucks in queue
        
        w = new Warehouse(0, new Point(0, 0), 2);
        Truck t1 = new Truck(0, new Point(0, 0), 5);
        Truck t2 = new Truck(1, new Point(0, 0), 5);
        
        w.joinQueue(t1);
        w.joinQueue(t2);
        
        assertFalse(w.getDocks().get(0).isOccupied());
        assertFalse(w.getDocks().get(1).isOccupied());
        assertEquals(2, w.getNumQueuingTrucks());
        
        w.action();
        assertFalse(w.getDocks().get(0).isOccupied());
        assertFalse(w.getDocks().get(1).isOccupied());
        assertEquals(0, w.getNumQueuingTrucks());
        
        // 2 docks, 3 trucks in queue
        
        w = new Warehouse(0, new Point(0, 0), 2);
        t1 = new Truck(0, new Point(0, 0), 5);
        t2 = new Truck(1, new Point(0, 0), 5);
        Truck t3 = new Truck(2, new Point(0, 0), 5);
        
        w.joinQueue(t1);
        w.joinQueue(t2);
        w.joinQueue(t3);
        
        assertFalse(w.getDocks().get(0).isOccupied());
        assertFalse(w.getDocks().get(1).isOccupied());
        assertEquals(3, w.getNumQueuingTrucks());
        
        w.action();
        assertFalse(w.getDocks().get(0).isOccupied());
        assertFalse(w.getDocks().get(1).isOccupied());
        assertEquals(1, w.getNumQueuingTrucks());
        
        w.action();
        assertFalse(w.getDocks().get(0).isOccupied());
        assertFalse(w.getDocks().get(1).isOccupied());
        assertEquals(0, w.getNumQueuingTrucks());
        
        // 2 docks, 3 trucks queued (one pick-up and one dropoff each)
        
        w = new Warehouse(0, new Point(0, 0), 2);
        t1 = new Truck(0, new Point(0, 0), 5);
        t2 = new Truck(1, new Point(0, 0), 5);
        t3 = new Truck(2, new Point(0, 0), 5);
        
        Manifest m1 = new Manifest(), m2 = new Manifest(), m3 = new Manifest();
        
        m1.add(new Shipment(0, w, new Warehouse(1, new Point(7, 5), 2), 1));
        m2.add(new Shipment(1, w, new Warehouse(1, new Point(7, 5), 2), 1));
        m3.add(new Shipment(2, w, new Warehouse(1, new Point(7, 5), 2), 1));
        
        t1.setManifest(m1);
        t2.setManifest(m2);
        t3.setManifest(m3);
        
        t1.load(new Shipment(3, null, w, 1));
        t2.load(new Shipment(4, null, w, 1));
        t3.load(new Shipment(5, null, w, 1));
        
        assertEquals(1, t1.getHoldSize());
        assertEquals(1, t2.getHoldSize());
        assertEquals(1, t3.getHoldSize());
        
        w.joinQueue(t1);
        w.joinQueue(t2);
        w.joinQueue(t3);
        
        assertEquals(0, w.getNumOccupiedDocks());
        assertEquals(3, w.getNumQueuingTrucks());
        
        w.action();
        
        assertEquals(2, w.getNumOccupiedDocks());
        assertEquals(1, w.getNumQueuingTrucks());
        assertEquals(0, t1.getHoldSize());
        assertEquals(0, t2.getHoldSize());
        assertEquals(1, t3.getHoldSize());
        
        w.action();
        
        assertEquals(0, w.getNumOccupiedDocks());
        assertEquals(1, w.getNumQueuingTrucks());
        assertEquals(1, t1.getHoldSize());
        assertEquals(1, t2.getHoldSize());
        assertEquals(1, t3.getHoldSize());
        
        w.action();
        
        assertEquals(1, w.getNumOccupiedDocks());
        assertEquals(0, w.getNumQueuingTrucks());
        assertEquals(1, t1.getHoldSize());
        assertEquals(1, t2.getHoldSize());
        assertEquals(0, t3.getHoldSize());
        
        w.action();
        
        assertEquals(0, w.getNumOccupiedDocks());
        assertEquals(0, w.getNumQueuingTrucks());
        assertEquals(1, t1.getHoldSize());
        assertEquals(1, t2.getHoldSize());
        assertEquals(1, t3.getHoldSize());
        
        
        
    }
}
