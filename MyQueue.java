import java.util.NoSuchElementException;

/**
 * A basic implementation of a queue using an {@link ItemList} as a backing data structure.
 * 
 * @author Jackson Eshbaugh
 * @version 05/03/2024
 */
public class MyQueue<E> implements BasicQueue<E> {
    
    private ItemList<E> items = new ItemList<E>();
    
    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    @Override
    public int size() {
        return items.size();
    }
    
    @Override
    public E peek() {
        if(items.isEmpty()) return null;
        return items.get(0);
    }
    
    @Override
    public E poll() {
        if(items.isEmpty()) return null;
        
        E ret = items.get(0);
        
        for(int i = 0; i < size() - 1; ++i) {
            items.set(i, items.get(i + 1));
        }
        
        items.remove(size() - 1);
        return ret;
    }
    
    @Override
    public E remove() {
        if(this.isEmpty()) throw new NoSuchElementException("The queue is empty.");
        
        return poll();
    }
    
    @Override
    public boolean add(E value) {
        if(value == null) throw new NullPointerException("Can't add a null pointer to the queue.");
        items.add(value);
        return true;
    }
}