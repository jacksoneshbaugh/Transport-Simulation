import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Classe-test ManifestTest.
 *
 * @author Jackson Eshbaugh
 * @version 05/01/2024
 */
public class ManifestTest {

    /**
    * Tests the {@link Manifest#add(Shipment)}.
    * Creates a manifest and adds shipments, ensuring that {@link Manifest#size()} is correct.
    */
    @Test
    @DisplayName("add(Shipment)")
    public void testAdd() {

        Manifest m = new Manifest();

        assertEquals(0, m.size());

        m.add(new Shipment(0, null, null, 1));
        assertEquals(1, m.size());

        m.add(new Shipment(0, null, null, 1));
        assertEquals(2, m.size());

        m.add(new Shipment(0, null, null, 1));
        assertEquals(3, m.size());

    }

    /**
    * Tests the {@link Manifest#nextPickUp(Point)}.
    * Creates a manifest and adds shipments, ensuring that {@link Manifest#size()} is correct. Then, calls
    * {@code nextPickUp()} and ensures that the closest shipment to the given point is returned.
    */
    @Test
    @DisplayName("nextPickUp(Point)")
    public void testNextPickUp() {

        Manifest m = new Manifest();

        Shipment a = new Shipment(0, new Warehouse(0, new Point(3, 0), 2), null, 1), 
        b = new Shipment(1, new Warehouse(0, new Point(5, 0), 2), null, 1);

        assertEquals(0, m.size());

        m.add(a);
        assertEquals(1, m.size());

        m.add(b);
        assertEquals(2, m.size());
        
        assertEquals(a, m.nextPickUp(new Point(2, 0)));
        assertEquals(b, m.nextPickUp(new Point(4, 0))); // Equidistant case
        assertEquals(b, m.nextPickUp(new Point(6, 0)));
        
        assertEquals(2, m.size());

    }
    
    /**
    * Tests the {@link Manifest#pickUp(Point)}.
    * Creates a manifest and adds shipments, ensuring that {@link Manifest#size()} is correct. Then, calls
    * {@code pickUp()} and ensures that the closest shipment to the given point is returned, and that the size
    * is decremented.
    */
    @Test
    @DisplayName("pickUp(Point)")
    public void testPickUp() {

        // Equidistant case
        
        Manifest m = new Manifest();

        Shipment a = new Shipment(0, new Warehouse(0, new Point(3, 0), 2), null, 1), 
        b = new Shipment(1, new Warehouse(0, new Point(5, 0), 2), null, 1);

        assertEquals(0, m.size());

        m.add(a);
        assertEquals(1, m.size());

        m.add(b);
        assertEquals(2, m.size());
        
        assertEquals(b, m.pickUp(new Point(4, 0)));
        assertEquals(1, m.size());
        
        assertEquals(a, m.pickUp(new Point(4, 0)));
        assertEquals(0, m.size());
        
        // General case
        
        m = new Manifest();
        
        a = new Shipment(0, new Warehouse(0, new Point(3, 0), 2), null, 1); 
        b = new Shipment(1, new Warehouse(0, new Point(5, 0), 2), null, 1);

        assertEquals(0, m.size());

        m.add(a);
        assertEquals(1, m.size());

        m.add(b);
        assertEquals(2, m.size());
        
        assertEquals(b, m.pickUp(new Point(6, 0)));
        assertEquals(1, m.size());
        
        assertEquals(a, m.pickUp(new Point(6, 0)));
        assertEquals(0, m.size());
    }
    
    
}
