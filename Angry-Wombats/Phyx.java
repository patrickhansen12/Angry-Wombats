import java.util.List;
/**
 * Not our code.
 * Simplified physics class for this scenario
 * 
 * @author Ben 
 * @version 1
 */
public class Phyx  
{
    // instance variables - replace the example below with your own
    private static Vect gravity = new Vect(0,0.6);
    
    /**
     * Constructor for objects of class Phyx
     */
    private Phyx()
    {
    }
    
    /**
     * Returns a change in X position based on current velocity and forces acting on 
     * the object.  Overloaded function, one takes a vect, the other takes a list of vects
     */
    public static Vect getVelocity(Vect velocity,List<Vect> forces) {
        // so a force is just a change in velocity.  i will ignore the mass part
        // (technically a change in velocity is force/mass).  Pretty simple, just add
        // all the forces to the velocity and return the vector
        
        for (Vect v: forces) {
            velocity.add(v);
        }
        
        return velocity;
    }
    // apparently, air friction is usually proportional to the velocity, or the velocity squared.  Well, for 
    // this demo, I will just make it proportional to the linear velocity
    public static Vect airFriction(Vect velocity, double number) {
        Vect newvect = new Vect(-velocity.getX()*number,-velocity.getY()*number);
        return newvect;      
    }
    
    public static Vect elasticForce(Vect displacement, double forceConstant) {
        double forceX = -forceConstant*displacement.getX();
        double forceY = -forceConstant*displacement.getY();
        Vect theForce = new Vect(forceX,forceY);
        return theForce;
    }
    // for when mass doesn't matter, like with gravity
    public static Vect getVelocity(Vect velocity, Vect force) {
        velocity.add(force);
        return velocity;
    }
    
    // for when mass matters, like when it's being flung out of a slinghot, or facing
    // air resistence
    public static Vect getVelocity(Vect velocity,Vect force,double mass) {
         Vect acceleration = new Vect(force.getX()/mass,force.getY()/mass);
         velocity.add(acceleration);
         return velocity;
    }
    
    public static void setGravity(double g) {
        gravity.setY(g);
    }
    
    public static Vect Gravity() {
        return gravity;
    }
}
