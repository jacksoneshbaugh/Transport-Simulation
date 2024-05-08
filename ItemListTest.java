import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Test class for {@link ItemList}.
 * 
 * @author Jackson Eshbaugh
 * @version 05/03/2024
 */
public class ItemListTest {
    public ItemListTest() {
        
    }

    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        
    }

    /**
     * Called after each test.
     */
    @AfterEach
    public void tearDown() {
        
    }
    
    
    /**
     * Tests the {@link ItemList<Integer>#reset()} method:
     * <ol>
     *   <li>Tests resetting the {@code counter}.</li>
     * </ol>
     */
    @Test
    @DisplayName("reset() Tests")
    public void testReset() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        
        for(int i = 0; i < 4; ++i) {
            list.add(i);
        }
        
        assertEquals(0, list.next());
        assertEquals(1, list.next());
        
        // reset the counter, then make sure the same behavior occurs.
        
        list.reset();
        
        assertEquals(0, list.next());
        assertEquals(1, list.next());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#next()} method:
     * <ol>
     *   <li>Tests when the {@code counter} is {@code 0} and the list is {@code 0} long.</li>
     *   <li>Tests when the {@code counter} is within the list's range.</li>
     *   <li>Tests when the {@code counter} is too large.</li>
     *   <li>Tests when an element is added to the index next to the counter.</li>
     *   <li>Tests when an element is removed at the index next to the counter.</li>
     *   <li>Tests when an element is added to the index the counter points to.</li>
     *   <li>Tests when an element is removed at the index the counter points to.</li>
     * </ol>
     */
    @Test
    @DisplayName("next() Tests")
    public void testNext() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        boolean tooLargeFlag = false, zeroFlag = false;
        
        // Both list size and counter are 0.
        
        try {
            list.next();
        } catch (Exception e) {
            zeroFlag = true;
        }
        
        list.reset();
        
        for(int i = 0; i < 6; ++i) {
            list.add(i);
        }
        
        // 0 1 5 32 4 5
        
        assertEquals(0, list.next());
        assertEquals(1, list.next());
        
        // Add next to the pointer
        list.add(2, 5);
        
        assertEquals(5, list.next());
        
        // Remove next to the pointer
        
        assertEquals(2, list.remove(3));
        
        assertEquals(3, list.next());
        
        // Add at the pointer
        list.add(3, 32);
        
        assertEquals(3, list.next());
        
        // Remove at the pointer
        assertEquals(3, list.remove(4));
        
        assertEquals(5, list.next());
        
        // Counter is out of bounds for list.
        
        try {
            list.next();
        } catch (Exception e) {
            tooLargeFlag = true;
        }
        
        assertTrue(zeroFlag);
        assertTrue(tooLargeFlag);
    }
    
    /**
     * Tests the {@link ItemList<Integer>#toString()} method:
     * <ol>
     *   <li>Tests an empty list.</li>
     *   <li>Tests appending, prepending, and adding elements to the middle.</li>
     *   <li>Tests removing some elements.</li>
     * </ol>
     */
    @Test
    @DisplayName("toString() Tests")
    public void testToString() {
        ItemList<Integer> list = new ItemList<Integer>();
        
        assertEquals("<>", list.toString());
        
        list.add(1);
        assertEquals("<1>", list.toString());
        
        list.add(0, 2);
        assertEquals("<2 1>", list.toString());
        
        list.add(1, 3);
        assertEquals("<2 3 1>", list.toString());
        
        list.add(1, 4);
        assertEquals("<2 4 3 1>", list.toString());
        
        assertEquals(1, list.remove(3));
        assertEquals("<2 4 3>", list.toString());

        assertEquals(2, list.remove(0));
        assertEquals("<4 3>", list.toString());
        
        assertEquals(3, list.remove(1));
        assertEquals("<4>", list.toString());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#get(int)} method:
     * <ol>
     *   <li>Tests an {@code index} too large for the list's boundaries.</li>
     *   <li>Tests an {@code index} too small for the list's boundaries.</li>
     *   <li>Tests an {@code index} within the list's boundaries.</li>
     *   <li>Tests an {@code index} at the beginning the list's boundary (i.e., {@code 0}).</li>
     *   <li>Tests an {@code index} at the end of the list's boundary (i.e., {@link ItemList<Integer>#size()} {@code - 1}).</li>
     * </ol>
     */
    @Test
    @DisplayName("get() Tests")
    public void testGet() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        boolean lowerBoundException = false, upperBoundException = false;
        
        for(int i = 0; i < 4; ++i) {
            list.add(i);
        }
        
        try {
            list.get(-1);
        } catch (Exception e) {
            lowerBoundException = true;
        }
        
        try {
            list.get(list.size());
        } catch (Exception e) {
            upperBoundException = true;
        }
        
        assertTrue(lowerBoundException);
        assertTrue(upperBoundException);
        
        // Middle of the list
        assertEquals(2, list.get(2));
        assertEquals(1, list.get(1));
        
        // Beginning and end of list
        assertEquals(0, list.get(0));
        assertEquals(3, list.get(list.size() - 1));
        
    }
    
    
    /**
     * Tests the {@link ItemList<Integer>#clear()} method:
     * <ol>
     *   <li>Tests clearing an empty list.</li>
     *   <li>Tests clearing a list with some elements.</li>
     *   <li>Tests clearing a list that has been expanded.</li>
     *   <li>Tests clearing a list that has been shrunk.</li>
     * </ol>
     */
    @Test
    @DisplayName("clear() Tests")
    public void testClear() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        
        // Empty list
        
        list.clear();
        
        assertEquals("<>", list.toString());
        assertEquals(0, list.size());
        
        // List with a few items added
        
        list.add(0);
        list.add(1);
        
        assertEquals("<0 1>", list.toString());
        assertEquals(2, list.size());
        
        list.clear();
        
        assertEquals("<>", list.toString());
        assertEquals(0, list.size());
        
        // Expanded list
        
        for(int i = 0; i < 12; ++i) {
            list.add(i);
        }
        
        assertEquals("<0 1 2 3 4 5 6 7 8 9 10 11>", list.toString());
        assertEquals(12, list.size());
        
        list.clear();
        
        assertEquals("<>", list.toString());
        assertEquals(0, list.size());
        
        // Expanded and shrunk list
        
        for(int i = 0; i < 12; ++i) {
            list.add(i);
        }
        
        assertEquals(11, list.remove(11));
        assertEquals(10, list.remove(10));
        assertEquals(9, list.remove(9));
        
        assertEquals("<0 1 2 3 4 5 6 7 8>", list.toString());
        assertEquals(9, list.size());
        
        list.clear();
        
        assertEquals("<>", list.toString());
        assertEquals(0, list.size());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#isEmpty()} method:
     * <ol>
     *   <li>Tests an empty list.</li>
     *   <li>Tests a list after adding some elements.</li>
     *   <li>Tests a list after adding and removing elements.</li>
     * </ol>
     */
    @Test
    @DisplayName("isEmpty() Tests")
    public void testIsEmpty() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        
        assertTrue(list.isEmpty());
        
        // Add some elements
        for(int i = 0; i < 4; ++i) {
            list.add(i);
        }
        
        assertFalse(list.isEmpty());
        
        // Clear the array without using clear()
        
        for(int i = 3; i >= 0; --i) {
            list.remove(i);
        }
        
        assertTrue(list.isEmpty());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#size()} method:
     * <ol>
     *   <li>Tests an empty list's size.</li>
     *   <li>Tests a list's size after adding some elements.</li>
     *   <li>Tests a list's size after expanding, then shortening the array.</li>
     * </ol>
     */
    @Test
    @DisplayName("size() Tests")
    public void testSize() {
        
        ItemList<Integer> list = new ItemList<Integer>();
        
        assertEquals(0, list.size());
        
        // Expand the array.
        for(int i = 0; i < 12; ++i) {
            list.add(i);
        }
        
        assertEquals(12, list.size());
        
        // Shorten the array.
        assertEquals(11, list.remove(11));
        assertEquals(10, list.remove(10));
        assertEquals(9, list.remove(9));
        assertEquals(8, list.remove(8));
        
        assertEquals(8, list.size());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#add(int)} and {@link ItemList<Integer>#add(int, int)}
     * methods:
     * <ol>
     *   <li>Tests appending to the list.</li>
     *   <li>Tests prepending to the list.</li>
     *   <li>Tests adding to the middle of the list.</li>
     *   <li>Tests an index too low and too high for the list.</li>
     * </ol>
     */
    @Test
    @DisplayName("add() Tests")
    public void testAdd() {
        ItemList<Integer> list = new ItemList<Integer>();
        
        list.add(1);
        assertEquals("<1>", list.toString());
        
        list.add(0, 2);
        assertEquals("<2 1>", list.toString());
        
        list.add(1, 3);
        assertEquals("<2 3 1>", list.toString());
        
        list.add(1, 4);
        assertEquals("<2 4 3 1>", list.toString());
        
        boolean tooLow = false, tooHigh = false;
        
        try { 
            list.add(-1, 8); 
        } catch (Exception e) {
            tooLow = true;
        }
        
        try { 
            list.add(15, 8); 
        } catch (Exception e) {
            tooHigh = true;
        }
        
        assertTrue(tooLow);
        assertTrue(tooHigh);
    }
    
    /**
     * Tests the {@link ItemList<Integer>#remove(int)} method:
     * <ol>
     *   <li>Tests removing a value from the end of the list.</li>
     *   <li>Tests removing a value from the beginning the list.</li>
     *   <li>Tests removing a value from the middle of the list.</li>
     *   <li>Tests removing using an index above the boundary of the list.</li>
     *   <li>Tests removing using an index below the boundary of the list.</li>
     * </ol>
     */
    @Test
    @DisplayName("remove() Tests")
    public void testRemove() {
        ItemList<Integer> list = new ItemList<Integer>();
        boolean upperOut = false, lowerOut = false;
        
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        
        try {
            list.remove(31);
        } catch (Exception e) {
            upperOut = true;
        }
        
        try {
            list.remove(-1);
        } catch (Exception e) {
            upperOut = true;
        }
        
        assertEquals("<1 2 3 4 5 6>", list.toString());
        
        assertEquals(6, list.remove(5));
        assertEquals("<1 2 3 4 5>", list.toString());
        
        assertEquals(1, list.remove(0));
        assertEquals("<2 3 4 5>", list.toString());
        
        assertEquals(3, list.remove(1));
        assertEquals("<2 4 5>", list.toString());
    }
    
    /**
     * Tests the {@link ItemList<Integer>#array_size()} method:
     * <ol>
     *   <li>Tests a newly created array.</li>
     *   <li>Tests an array that has been resized to be larger.</li>
     *   <li>Tests an array that has been resized to be smaller.</li>
     * </ol>
     * 
     * <i>The success of this test implies that the {@code private}
     * {@link ItemList<Integer>#resize(int)} method is also functioning
     * correctly.</i>
     */
    @Test
    @DisplayName("array_size() Tests")
    public void testArraySize() {
        ItemList<Integer> list = new ItemList<Integer>();
        
        assertEquals(10, list.array_size());
        
        // Expand the array.
        for(int i = 0; i < 12; ++i) {
            list.add(i);
        }
        
        assertEquals(20, list.array_size());
        
        // Shorten the array.
        assertEquals(11, list.remove(11));
        assertEquals(10, list.remove(10));
        assertEquals(9, list.remove(9));
        
        assertEquals(10, list.array_size());
        
    }
    
    /**
     * Tests the {@link ItemList<Integer>#empty_cnt()} method:
     * <ol>
     *   <li>Tests a newly created array's count of empty cells (should be {@code 10}).</li>
     *   <li>Tests an array that has been resized to be larger.</li>
     *   <li>Tests an array that has been resized to be smaller.</li>
     * </ol>
     */
    @Test
    @DisplayName("empty_cnt() Tests")
    public void testEmptyCount() {
        ItemList<Integer> list = new ItemList<Integer>();
        
        assertEquals(10, list.empty_cnt());
        
        // Expand the array.
        for(int i = 0; i < 12; ++i) {
            list.add(i);
        }
        
        assertEquals(8, list.empty_cnt());
        
        // Shrink the array.
        for(int i = 11; i >= 0; --i) {
            list.remove(i);
        }
        
        assertEquals(10, list.empty_cnt());
        
    }
    
    
}
