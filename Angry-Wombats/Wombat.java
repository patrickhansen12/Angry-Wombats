import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;

/**
 * We have copied a slingshot base with physics. Most of these are not ours. 
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
    double grav = 0.6; // yes, the wombat needs to know the gravity to launch itself into the slingshot
    double airfriction = 0.1; // the wombat needs to know the air friction too, since it controlls it's own flight
    double speed; double distance;
    boolean movingright = false;
    boolean senttosshot = false;
    boolean shotcontrol = false;
    boolean launched = false;
    boolean dothisonce = false;
    boolean removed = false; // set to true if it goes too high
    GreenfootImage clr = new GreenfootImage(1,1);// a clear image incase it goes to high
    WombatWorld t;// = new WombatWorld();
    
    //our code.
    private boolean touchedObstacle = false;
    private Target touchedTarget;
    //not our code.
    boolean dotscheduled = false;
    boolean takenaway = false; // gets set to true when the wombat gets removed! (so the timer doesn't draw a dot)

    Vect velocity = new Vect(10,-10);
    Vect windVect = new Vect(0,0);
    
    public Wombat() {
  
        mass = 5; 
    }
    
    /**
     * Act - do whatever the Wombat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
        if (!initialized) {
            initialize();
        }
        //our code. get the touched target as an actor, converts back to Target
        touchedTarget = (Target) getOneTouchedObject(Target.class);
        //checks if wombat touched a target.
        if(this != null) { //just to surely avoid NullPointers...
            checkObstacle();
        }
        move();
    }    
    
    /**Moves the wombat using physics. Not our code but modified. **/
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
           
            if ((getX() >= getWorld().getWidth()-1) || (getX() <= 1) || (velocity.getX() == 0)) {
                removeWombat(); // multiple steps... needs it's own method
            } else if (getY() >= getWorld().getHeight()-1) {
                // also, remove if it drops off the screen
                removeWombat();
            }
        }
    }
    
    /** Gets the mass of the wombat. Returns a double. Not our code. **/
    public double getMass() {
        return mass;
    }
    
    /** Removes the current wombat and reloads the slingshot Modified code.**/
    public void removeWombat() {
        getWorld().removeObject(this); // remove this object
        //t = (WombatWorld) getWorld();
        //t.moveRight();
        //t.addAnotherWombat(); // add another wombat, & reload ths slingshot!
        t.reload(); //our code. reload slingshot without addAnotherWombat()
        takenaway = true;
    }
    
    /** Shoots the slingshot. Modified to play shooting sound. **/
    public void release(Vect veloc) {
        Greenfoot.playSound("birdy.wav");
        launched = true;
        shotcontrol = false;
        velocity.setEqualTo(veloc);
    }
    
    /** Makes next wombat jump in the slingshot. Not our code **/
    public void sendToSShot(){
        // ok these numbers work when gravity is 4, so set it to 4 then set it back
        Phyx.setGravity(4);
        velocity.setX(3.8);
        velocity.setY(-7.5);
        senttosshot = true;
        shotcontrol = true;
    }
    
    /** Gets current angle of the wombat. Not our code, modified to harden the game **/
    public int getAngle() {
       
        int vx,vy; // the velocity x, and the velocity y
        vx = vy = 0; // to get rid of compiler warnings
        if (velocity.getX() >= 0) { 
            // great!  arctangent works.  The angle is computed by considering the velocitites in the x and
            // y direction
            vx = (int) velocity.getX();
            return (int) (Math.atan(-velocity.getY()/velocity.getX())*180/Math.PI);
        } else { 
        
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
       
    }
   
    //not our code....
    public void setXandY(double a, double b) {
        x = a;
        y = b;
    }
    
    public void moveRight(double spd, double dist) {
        speed = spd;
        distance = x + dist;
        movingright = true;
    }
    
   
    
    public void initialize() {
        x = getX(); y = getY(); // get the initial location, whatever that might be
        t = (WombatWorld) getWorld();
        initialized = true;
    }
    
    //our code. checks if wombat hit a target. Tells target if it got hit.
    /** Checks if the wombat hit a target. If yes, tells the target that it got hit. **/
    public void checkObstacle() {
        if(touchedTarget != null && this.touch(touchedTarget) && !touchedObstacle) { 
            touchedTarget.gotHit();
            this.touchedObstacle = true;
        }
    }
    
    /*
     * ============== Collision sensor thing ================
     * This is a downloaded pixel perfect collision sensor method collection. 
     * Found on greenfoot.org
     * Used to check if target and wombat are touching. Easy to use because of return types.
     */
    
    
    /** This method is a pixel perfect collision detection. Returns a List of all Actors, that are touched by this object */
    public List getTouchedObjects(Class clss)
    {
        List<Actor> list =
            getWorld().getObjects(clss),
        list2 = new ArrayList();
        for(Actor A : list)
            if(intersects(A)&&touch(A))
                list2.add(A);
        return list2;
    }

    /** This method is a pixel perfect collision detection. Return, if it intersects an actor of the given class */
    public boolean touch(Class clss)
    {
        List<Actor> list =
        getWorld().getObjects(clss),
        list2 = new ArrayList();
        for(Actor A : list)
            if(intersects(A)&&touch(A))
                return true;
        return false;
    }

    /** This method is a pixel perfect collision detection. It returns a touched actor of the given class, if there's one touched. */
    public Actor getOneTouchedObject(Class clss)
    {
        List<Actor> list =
            getWorld().getObjects(clss),
        list2 = new ArrayList();
        for(Actor A : list)
            if(intersects(A)&&touch(A))
                return A;
        return null;
    }

    /** This method is a pixel perfect collision detection. Returns true, if the object touchs the given Actor */
    public boolean touch(Actor a_big)
    {
        Actor a_small;
        if(getImage().getWidth()*getImage().getHeight()>a_big.getImage().getHeight()*a_big.getImage().getWidth())
        {
            a_small=a_big;
            a_big=this;
        }
        else
            a_small=this;

        int i_hypot=(int)Math.hypot(a_small.getImage().getWidth(),a_small.getImage().getHeight());

        GreenfootImage i=new GreenfootImage(i_hypot,i_hypot);
        i.drawImage(a_small.getImage(),i_hypot/2-a_small.getImage().getWidth()/2,i_hypot/2-a_small.getImage().getHeight()/2);
        i.rotate(a_small.getRotation());
        int w=i_hypot;

        GreenfootImage Ai = a_big.getImage(),
        i2=new GreenfootImage(i_hypot=(int)Math.hypot(Ai.getWidth(),Ai.getHeight()),i_hypot);
        i2.drawImage(Ai,i2.getWidth()/2-Ai.getWidth()/2,i2.getHeight()/2-Ai.getHeight()/2);
        i2.rotate(a_big.getRotation());
        Ai=i2;

        int
        x_Offset=a_big.getX()-a_small.getX()-(Ai.getWidth()/2-w/2),
        y_Offset=a_big.getY()-a_small.getY()-(Ai.getHeight()/2-w/2);

        boolean b = true;
        for(int yi =Math.max(0,y_Offset); yi<w && yi<i_hypot+y_Offset && b; yi++)
            for(int xi =Math.max(0,x_Offset); xi<w && xi<i_hypot+x_Offset && b; xi++)
                if(Ai.getColorAt(xi-x_Offset,yi-y_Offset).getAlpha()>0 && i.getColorAt(xi,yi).getAlpha()>0)
                    b=false;
        return !b;
    }
}
