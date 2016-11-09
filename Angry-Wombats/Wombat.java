 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Timer;
import java.util.TimerTask;

/**
 * Write a description of class Wombat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wombat  extends Actor
{
    // have to initialize the location
    boolean initialized = false;
    double x; double y; // the x and y variables
    static double mass; // the mass of the wombat
    double grav = 0.6; // yes, the wombat needs to know the gravity to launch itself into the slingshot I tried making
    // it different, but it got hard
    double airfriction = 0.1; // the wombat needs to know the air friction too, since it controlls it's own flight
    double speed; double distance;
    boolean movingright = false;
    boolean senttosshot = false;
    boolean shotcontrol = false;
    boolean launched = false;
    boolean dothisonce = false;
    boolean removed = false; // set to true if it goes too high
    GreenfootImage clr = new GreenfootImage(1,1);// a clear image incase it goes to high
    TestWorld t;// = new TestWorld();
   
    boolean dotscheduled = false;
    boolean takenaway = false; // gets set to true when the wombat gets removed! (so the timer doesn't draw a dot)

    Vect velocity = new Vect(10,-10);
    Vect windVect = new Vect(0,0);
    
    public Wombat() {
  
        mass = 5; // some number
    }
    
    /**
     * Act - do whatever the Wombat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        if (!initialized) {
            initialize();
        }
        
        move();
    }    
    
    public void move() {
        if (movingright) {
            x += speed;
            
            if (x > distance) {
                x = getX();
                movingright = false;
            }
            
            setLocation((int) x,(int) y);
        }
        else if (senttosshot) {
            // make the wombat go to the sling shot
            velocity = Phyx.getVelocity(velocity,Phyx.Gravity());
            x += velocity.getX();
            y += velocity.getY();
            setLocation((int) x,(int) y);
            // test if it got to the slingshot yet
            if (Math.abs(getX()-130)<=velocity.getX()) {
                x = 130; y = 340;
                setLocation((int) x,(int) y);
                senttosshot = false; // move on to the next stage
                shotcontrol = true;
            }
        } else if (shotcontrol) {
            // the slingshot controls the wombat after it is loaded, and before it gets lauched off.  Just set
            // the location.  I guess the slingshot could even do this part, but I think it's more clear this way
            setLocation((int) x,(int) y);
        }  
        if (launched) {
            // actually making it fly through the air is pretty simple
            if (!dothisonce) {
                dothisonce = false;
                Phyx.setGravity(grav); // set gravity back to what it's actually supposed to be
            }
            // okay this is the part that leaves a dot trail
            shotcontrol = false;  // something was causing a bug, but this fixes it
            velocity = Phyx.getVelocity(velocity,Phyx.Gravity()); // get the new velocity
            // unlike gravity, where it ends up we can ignore mass, we need to take it into account with air friction
            velocity = Phyx.getVelocity(velocity,Phyx.airFriction(velocity,airfriction),mass);
            // now add the force of the wind
            velocity = Phyx.getVelocity(velocity,windVect,mass);
            x += velocity.getX();
            y += velocity.getY();
            setLocation((int) x,(int) y);
            // make clear if it goes too high.  This must be done before anything else, incase it gets removed
            if (getY() <= 1) {
                 setImage(clr); // a clear image
                 removed  = true;
            } else {
                if (removed) {
                    setImage("wombat.png");
                }
            }
            // finally, test to see if it is off the screen.  It will never actually move off the screen, so see
            // if it's witin one pixel.
            if ((getX() >= getWorld().getWidth()-1) || (getX() <= 1) || (velocity.getX() == 0)) {
                removeWombat(); // multiple steps... needs it's own method
            } else if (getY() >= getWorld().getHeight()-1) {
                // also, remove if it drops off the screen
                removeWombat();
            }
        }
    }
    
    public double getMass() {
        return mass;
    }
    
    public void removeWombat() {
        getWorld().removeObject(this); // remove this object
        //t = (TestWorld) getWorld();
        //t.moveRight();
        t.addAnotherWombat(); // add another wombat, & reload ths slingshot!
        takenaway = true;
    }
    
    public void release(Vect veloc) {
        Greenfoot.playSound("birdy.mp3");
        launched = true;
        shotcontrol = false;
        velocity.setEqualTo(veloc);
    }
    
    public void sendToSShot(){
        // ok these numbers work when gravity is 0.6, so set it to 0.6 then set it back
        Phyx.setGravity(4);
        velocity.setX(3.8);
        velocity.setY(-7.5);
        // now set it back to whatever it was.  Don't do this until launched though. 
        //Phyx.setGravity(grav); // don't do this now...wait until later!!! (in move(), if (launched).. etc..)
        senttosshot = true;
        shotcontrol = true;
    }
    
    public int getVelocity() {
        return (int) velocity.magnitude();
    }
    
    public int getAngle() {
        // the problem with arctan is it only works from -90 to + 90 degrees.  Needs to be modified
        // I HATE HOW Y IS POSITIVE GOING DOWN IT JUST MESSES ME UP SO MUCH
        int vx,vy; // the velocity x, and the velocity y
        vx = vy = 0; // to get rid of compiler warnings
        if (velocity.getX() >= 0) { 
            // great!  arctangent works.  The angle is computed by considering the velocitites in the x and
            // y direction
            vx = (int) velocity.getX();
            // USE THE NEGITIVE OF Y!!! GAHHHHH 
            return (int) (Math.atan(-velocity.getY()/velocity.getX())*180/Math.PI);
        } else { 
            // well, I guess I can just do it case by case
            if (velocity.getY() >= 0) {
                // ok so we are in the 2nd quadrant.  so, pretend velocity is positive, get the angle,
                // then subtract it from 180. USE THE NEGITIVE OF Y BECAUSE NEGITIVE IS POSITIVE
                return (int) (180-(Math.atan(-velocity.getY()/-velocity.getX())*180/Math.PI));
            } else {
                // ok we are in the 3rd quadrant.  so, pretend they are both positive, then subtract
                // from 180, then make the whole thing negitive.  Actually, the negitives cancel, so
                // we don't have to pretend the veolicities are positive
                return (int) -(180-(Math.atan(-velocity.getY()/velocity.getX())*180/Math.PI));
            }
        }
        // gotta return soemthing!
        //return (int) (Math.atan(velocity.getY()/velocity.getX())*180/Math.PI);
    }
    /*
     * This will probabily be replaced later, but it knocks the wombat back
     */
    public void knockBack() {
        velocity.setX(-velocity.getX()); // just reverse the direction
    }
    
    public void setGravity(double gravity) {
        grav = gravity;
    }
    
    public void setAirFriction(double amt) {
        airfriction = amt;
    }
    
    public void setXandY(double a, double b) {
        x = a;
        y = b;
    }
    
    public void moveRight(double spd, double dist) {
        speed = spd;
        distance = x + dist;
        movingright = true;
    }
    
    public double mass() {
        return mass;
    }
    
    public void setMass(double input) {
        mass = input;
    }
    
    public void setWind(Vect wind) {
        windVect = wind;
    }
    
    public void initialize() {
        x = getX(); y = getY(); // get the initial location, whatever that might be
        t = (TestWorld) getWorld();
        initialized = true;
    }
    
    public class makeDot extends TimerTask
    {
        public void run() {
            if (!takenaway) {
              
            }
            dotscheduled = false;
        }
    }
}
