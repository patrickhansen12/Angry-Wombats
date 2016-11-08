 

/**
 * Vect is a 2D vector class. You can set the x and y, get the magnitude, and add 'em
 * 
 * @author Ben 
 * @version 1.0
 */
public class Vect  
{
    // instance variables - replace the example below with your own
    private double x;
    private double y;

    /**
     * default x and y values
     */
    public Vect()
    {
        x = 0;
        y = 0;
    }
    
    public Vect (double X, double Y) {
        x = X; 
        y = Y;
    }
    
    public void setX(double X) {
        x = X;
    }
    
    public void setY(double Y) {
        y = Y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    /**
     * adds another vector to this one..
     */
    public void add(Vect v) {
        x += v.getX();
        y += v.getY();
    }
    
    public void setEqualTo(Vect v) {
        x = v.getX();
        y = v.getY();
    }
    
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public void scalarMultiply(double num) {
        x = x*num;
        y = y*num;
    }
    // returns a unit vector of the vect
    public Vect returnUnit() {
        Vect a = new Vect(x,y); // clone this vector
        a.scalarMultiply(1/a.magnitude()); // divide by absolute size
        return a;
    }
    
    public double getAngle() {
        // returns the angle the vector is pointing.  Using a traditional coord system
        // similar to the ones they teach in math class.
        double X = x;
        double Y = -y; // because y goes in the oppisate direction as usual
        double theta = Math.acos(X/this.magnitude());
        double angle = theta*180.0/Math.PI;
        // this only works in the first two qudrants.  I forget what to do for the third
        // and fourth.  Time for a quick fix!
        if (Y<0) {
            angle = angle*-1;
        }
          return angle;
    }
    
    public void setXandY(double ex, double ey) {
        x = ex;
        y = ey;
    }
    
    public void setLength(double length) {
        x = x*(length/(this.magnitude()));
        y = y*(length/(this.magnitude()));
    }

}
