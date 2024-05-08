/**
 * Gives the general contract for a list used in this simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 05/01/2024
 */
public interface SimList<E> {
    
    /**
     * Adds the element to the list,
     * resizing the array if need be.
     * 
     * @param e the element to add to the list
     */
    void add(E e);
    
    /**
     * Adds the element to the list at the
     * specified index, moving elements further in the array.
     * Resizes the array if need be.
     * 
     * @param index the index in the list to add the element to
     * @param e the element to add to the list
     */
    void add(int index, E e);
    
    /**
     * Gets the list element at the specifed index.
     * 
     * @throws IndexOutOfBoundsException when the index is not in the boundary of [0, size() -1].
     * @param index the index from which to retrieve an element
     * @return the element at the specified index
     * 
     */
    E get(int index);
    
    /**
     * Clears the list and returns it to its initial state.
     */
    void clear();
    
    /**
     * Checks if there are any elements in the list (i.e., if the list's size is > 0).
     * 
     * @return {@code true} if the list is empty, and {@code false} if it has at least one element
     */
    boolean isEmpty();
    
    /**
     * Removes the list element at the specified location,
     * and adjusts the position of surrounding elements.
     * 
     * @return the list element that is removed
     */
    E remove(int index);
    
    /**
     * Gets the size of the list.
     * 
     * @return the size of the list
     */
    int size();
    
}