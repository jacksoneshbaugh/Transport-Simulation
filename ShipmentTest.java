import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Classe-test ShipmentTest.
 *
 * @author  Jackson Eshbaugh
 * @version 05/04/2024
 */
public class ShipmentTest {
    
    public ShipmentTest() { }
    
    /**
     * Tests the {@link Shipment#distanceToOrigin(Point)} method against pre-calculated distances.
     */
    @Test
    @DisplayName("distanceToOrigin(Point)")
    public void testDistanceToOrigin() {
        
        Point p = new Point(0, 0);
        Point q = new Point(1, 7);
        
        Shipment s1 = new Shipment(0, new Warehouse(0, new Point(3, 4), 2), null, 1);
        Shipment s2 = new Shipment(0, new Warehouse(0, new Point(0, 0), 2), null, 1);
        Shipment s3 = new Shipment(0, new Warehouse(0, new Point(0, 3), 2), null, 1);
        
        assertEquals(5, s1.distanceToOrigin(p));
        assertEquals(0, s2.distanceToOrigin(p));
        assertEquals(4, s3.distanceToOrigin(q));
        
    }
    
    /**
     * Tests the {@link Shipment#distanceToDestination(Point)} method against pre-calculated distances.
     */
    @Test
    @DisplayName("distanceToDestination(Point)")
    public void testDistanceToDestination() {
        
        Point p = new Point(0, 0);
        Point q = new Point(1, 7);
        
        Shipment s1 = new Shipment(0, null, new Warehouse(0, new Point(3, 4), 2), 1);
        Shipment s2 = new Shipment(0, null, new Warehouse(0, new Point(0, 0), 2), 1);
        Shipment s3 = new Shipment(0, null, new Warehouse(0, new Point(0, 3), 2), 1);
        
        assertEquals(5, s1.distanceToDestination(p));
        assertEquals(0, s2.distanceToDestination(p));
        assertEquals(4, s3.distanceToDestination(q));
        
    }
    
}
