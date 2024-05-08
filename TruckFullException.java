/**
 * An exception to be thrown if a truck's hold is full and an attempt is made to add a shipment.
 * 
 * @author Jackson Eshbaugh
 * @version 05/01/2024
 */
public class TruckFullException extends RuntimeException {
    
    /**
     * Create a new TruckFullException with the given message
     * 
     * @param message the error message to pass to the user
     */
    public TruckFullException(String message) {
        super(message);
    }
    
    /**
     * Create a new TruckFullException with no message
     */
    public TruckFullException() {
        super("");
    }
    
}