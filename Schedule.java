/**
 * Allows various elements of this simulation to implement an hourly schedule.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public interface Schedule {

    /**
     * Performs the hourly action of this element. Invoked each hour of the simulation.
     */
    void action();
    
    /**
     * Stores this element's current information into the log file.
     */
    void log();
    
}