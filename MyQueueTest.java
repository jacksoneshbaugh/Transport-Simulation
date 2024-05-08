import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MyQueue}.
 */
public class MyQueueTest {

    private MyQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new MyQueue<>();
    }

    /**
     * Tests the {@link MyQueue#isEmpty()} method.
     */
    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.add(1);
        assertFalse(queue.isEmpty());
        queue.remove();
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests the size of the queue is zero when the queue is newly created.
     */
    @Test
    void testSizeOnNewQueue() {
        assertEquals(0, queue.size());
    }

    /**
     * Tests the addition of elements and subsequent size increase.
     */
    @Test
    void testAdd() {
        assertTrue(queue.add(1));
        assertEquals(1, queue.size());
        assertTrue(queue.add(2));
        assertEquals(2, queue.size());
        assertTrue(queue.add(3));
        assertEquals(3, queue.size());
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    /**
     * Tests the {@link MyQueue#peek()} method, testing that peeking the next element does not remove it.
     */
    @Test
    void testPeek() {
        assertNull(queue.peek()); // Empty queue should return null on peek
        queue.add(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.size()); // Size should not change after peek
    }

    /**
     * Tests the {@link MyQueue#poll()} method, ensuring polling removes and returns the
     * first element, or returns null if the queue is empty.
     */
    @Test
    void testPoll() {
        assertNull(queue.poll()); // Empty queue should return null
        queue.add(1);
        queue.add(2);
        assertEquals(1, queue.poll());
        assertEquals(1, queue.size());
        assertEquals(2, queue.peek());
    }

    /**
     * Tests the {@link MyQueue#remove()} method to see if it throws an exception when
     * empty and behaves like poll otherwise.
     */
    @Test
    void testRemove() {
        assertThrows(NoSuchElementException.class, queue::remove); // Expect exception if queue is empty
        queue.add(1);
        assertEquals(1, queue.remove());
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests multiple operations to verify correct behavior of the queue under various conditions.
     */
    @Test
    public void fullTest() {
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.peek());
        assertFalse(queue.isEmpty());
        queue.remove();
        assertEquals(1, queue.size());
        assertEquals(3, queue.peek());
    }
}
