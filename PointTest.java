import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.lang.Math;

/**
 * Classe-test PointTest.
 *
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class PointTest {
    
    /**
     * Tests the {@link Point#distanceTo(Point)} method against pre-calculated distances.
     */
    @Test
    @DisplayName("distanceTo(Point)")
    public void testDistanceTo() {
        
        Point p = new Point(0, 0);
        Point q = new Point(3, 4);
        Point r = new Point(1, 7);
        Point s = new Point(0, 3);
        
        assertEquals(5, p.distanceTo(q));
        assertEquals(0, p.distanceTo(p));
        assertEquals(4, r.distanceTo(s));
        
    }
    
}
