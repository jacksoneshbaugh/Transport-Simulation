/**
 * Represents a point on the 2D simulation grid.
 * 
 * @author Jackson Eshbaugh
 * @version 04/29/2024
 */
public class Point {
    public int x, y;
    
    /**
     * Creates a new point at position ({@code x}, {@code y}).
     * 
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Calculates the distance from this point to the given {@code point}.
     * 
     * @param point the point to calcuate the distance to from here
     * @return the integer distance between these two points
     */
    public int distanceTo(Point point) {
        return (int) Math.round(Math.sqrt(Math.pow(point.y - y, 2) + Math.pow(point.x - x, 2)));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(!(o instanceof Point)) return false;
        
        Point p = (Point) o;
        
        return (p.x == this.x && p.y == this.y);
        
    }
}