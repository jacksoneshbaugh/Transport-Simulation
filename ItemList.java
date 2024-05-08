import java.util.Iterator;

/**
 * A basic ArrayList for the purposes of this simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 05/03/2024
 */
public class ItemList<E> implements SimList<E>, Iterable<E> {
    
    
    public class ItemListIterator implements Iterator<E> {
        
        private int i = 0;
        
        public E next() {
            ++i;
            return get(i - 1);
        }
        
        public boolean hasNext() {
            return i < size - 1;
        }
        
    }
    
    E[] list;
    int size;
    int counter;
    
    /**
     * Instantiates a new {@link IntArrayList} with
     * a 10 unit long array and {@code size} and {@code counter}
     * both initialized to {@code 0}.
     */
    public ItemList() {
        this.list = (E[]) new Object[10];
        this.size = 0;
        this.counter = 0;
    }
    
    private void resize(int newSize) {
        E[] newArray = (E[]) new Object[newSize];
        
        for(int i = 0; i < size; ++i) {
            newArray[i] = list[i];
        }
        
        list = newArray;
    }
    
    public Iterator<E> iterator() {
        return new ItemListIterator();
    }
    
    public int size() {
        return this.size;
    }
    
    public int array_size() {
        return list.length;
    }
    
    public int empty_cnt() {
        
        return list.length - this.size();
        
    }
    
    public void add(E e) {
        
        if(size == list.length) {
            // Need to expand the array.
            resize(size + 10);
        }
        
        // Simply add the element, and increase the size by 1.
        list[size] = e;
        size++;
    }
    
    public void add(int index, E e) {
        if(index < 0 || index > size) {
            // The index is out of range.
            throw new IndexOutOfBoundsException("Index out of bounds for ArrayList.");
        }
        
        if(size == list.length) {
            // Need to expand the array.
            resize(size + 10);
        }
        
        // Loop backwards through the array, moving every item over
        // one cell to the right.
        
        for(int i = size - 1; i >= index; --i) {
            list[i + 1] = list[i];
        } 
        
        // Insert the value at the specified index, which has now been vacated.
        list[index] = e;
        size++;
    }
    
    /**
     * Sets the value of a specific element in the list.
     * 
     * @param index the index to set
     * @param value the value to store at {@code index}
     */
    public void set(int index, E value) {
        
        if(index < 0 || index > size) {
            // The index is out of range.
            throw new IndexOutOfBoundsException("Index out of bounds for ArrayList.");
        }
        
        list[index] = value;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public void clear() {
        this.list = (E[]) new Object[10];
        this.size = 0;
        this.counter = 0;
    }
    
    public E remove(int index) {
        if(index < 0 || index >= size) {
            // The index is out of range.
            throw new IndexOutOfBoundsException("Index out of bounds for ArrayList.");
        }
        
        E value = list[index];        

        for(int i = index; i < size - 1; ++i) {
            list[i] = list[i + 1];
        }
        
        // Update the length of the array.
        size--;
        
        if(empty_cnt() > 10 && array_size() > 10) {
            // Need to shrink the array
            resize(array_size() - 10);
        }
        
        return value;
    }
    
    public E get(int index) {
        if(index < 0 || index >= size) {
            // The index is out of range.
            throw new IndexOutOfBoundsException("Index out of bounds for ArrayList.");
        }
        
        return list[index];
    }
    
    public String toString() {
        String str = "<";
        
        for(int i = 0; i < size; ++i) {
            str += list[i].toString() + " ";
        }
        
        str = str.trim() + ">";
        
        return str;
    }
    
    public void reset() {
        counter = 0;
    }
    
    public E next() {
        if(counter >= size) {
            throw new IndexOutOfBoundsException("End of stored data is reached.");
        }
        E value = get(counter);
        counter++;
        return value;
    }
    
}