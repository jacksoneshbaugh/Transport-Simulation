/**
 * An exception to be thrown if a truck's hold is empty and an attempt is made to remove a shipment.
 * 
 * @author Jackson Eshbaugh
 * @version 05/01/2024
 */
public class TruckEmptyException extends RuntimeException {
    
    /**
     * Create a new TruckEmptyException with the given message
     * 
     * @param message the error message to pass to the user
     */
    public TruckEmptyException(String message) {
        super(message);
    }
    
    /**
     * Create a new TruckEmptyException with no message
     */
    public TruckEmptyException() {
        super("");
    }
    
}
