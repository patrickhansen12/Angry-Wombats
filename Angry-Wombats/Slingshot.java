 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Not our slingshot but modified. Used our own image.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slingshot extends Actor
{
    // the images of the slingshot!!
    GreenfootImage sshot1 = new GreenfootImage("slingshot.png");
    GreenfootImage sshot2 = new GreenfootImage("slingshot.png");
    Wombat wombat; // the wombat that gets shot
    MouseInfo m; // need the mouse info....
    Strap s1, s2; // the straps of the slingshot
  
    Vect displacement = new Vect(0,0); // how far the straps get pulled
    Vect elasticforce = new Vect(0,0); // the force the slingshot puts on the wombat
    Vect velocity = new Vect(0,0); // the final velocity of the wombat before it gets launched
    Vect tempVect = new Vect(0,0); // a temp vect for comparison purposes..see shootwombat
    Vect anotherVect = new Vect(0,0);  // another vector.  This one is to make sure the straps don't get pulled too far.
    Vect anotherVectUnit = new Vect(0,0); // the unit vector of another vect
    WombatWorld t; // a pointer to the WombatWorld
    double wombatmass = 0;
    
    // other variables
    boolean initialized = false;
    boolean loaded = false;
    boolean firing = false;
    boolean fired = false;
    boolean wombatrectadded = false;
    boolean wombatrectremoved = false;
    double mousex;
    double mousey;
    double elasticnum = 0.4; // the elastic force constant, the higher this is, the more force
    double scalar;
    
    /**
     * Act - do whatever the Slingshot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //not our code.
        if (!initialized) {
            initialize();
        }
        // adding a wombat to the sling shot makes it loaded
        if (loaded) {
            doLoadedStuff(); // I do loaded stuff too, when I drink too much!
            if (Greenfoot.mouseClicked(null)) {
                // whoops, we are not loaded anymore!  Wish it was that easy in real life!
                loaded = false;
                firing = true;
            }
        } 
        
        if (firing) {
            shootWombat(); // shoot the dern thing 
        }
    }   
    
    /** Shoots the wombat. not our code. **/
    public void shootWombat() {
        // so, accelerate toward where the wombat got loaded, until the wombat passes that
        // point.  Get the wombat to move, then tell the straps to follow the wombat.
        // first, get the Force on the wombat.
        elasticforce = Phyx.elasticForce(displacement,elasticnum);
        // add the force to the velocity.  Actually I supppose the "correct" way to do this is add the Force/Mass
        wombatmass = wombat.getMass();
        velocity = Phyx.getVelocity(velocity,elasticforce,wombatmass); // this method can take mass into account
        // now that velocity has been computed, we know how fast the wombat is moving.
        // add the velocity to the displacement, and send the wombat to that new location.
        // also, move the straps.  Store the displacement before we alter it.
        tempVect.setEqualTo(displacement); // store the displacement variables here
        displacement.add(velocity);
        //wombat.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        s1.setThePoint(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        s2.setThePoint(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        // I tried to make the wombat rect move with the wombat, but greenfoot draws them at different times, so it didn't work.
        // just get rid of the wombat rect for now.
        
        wombat.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        // next we see if the wombat has passed the point where it was fired.  The wombat can be fired from
        // any direction.  Now when fireing, the displacement of the wombat is getting smaller.
        // if the displacement starts getting bigger, we know we should stop firing.
        // the displacement was stored in tempVect, so use that to compare.
        wombat.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        if (tempVect.magnitude() <= displacement.magnitude()) {
            wombat.release(velocity); // release the wombat at the velocity..
            // now that the wombat has been released, time to erase the old dots..
          
            firing = false; // stop manipulating the wombat...
            // now reset everything!
            s1.reset();
            s2.reset(); // all the booleans should already be reset, so just the straps
            velocity.setX(0); velocity.setY(0); // set all the vectors back to zero
            displacement.setX(0); displacement.setY(0);
            elasticforce.setX(0); elasticforce.setY(0);
            setImage(sshot1); // set the image back..
            getWorld().setPaintOrder(Wombat.class,Slingshot.class); // now set the draw order don't forget the dial!
            wombatrectadded = false;
            wombatrectremoved = false;
        }
        
    }
    // this endes up giving control of the wombat to the slingshot, once it gets loaded //not our code.
    public void addWombat(Wombat p) {
        wombat = p;
        loaded = true;
        
    }
    
    public void doLoadedStuff()
    {
        //not our code.
        // alright.  we should make sure the mouse is in the area of the slingshot
        //if (mouseInNeighborhood()) {
        //if (!wombatrectadded) {
        //    wombatrectadded = true;
        //    getWorld().addObject(pr,0,0);
        //   getWorld().setPaintOrder(Slingshot.class, WombatRect.class, Wombat.class);
        //}
            if (Greenfoot.mouseDragged(null)) {
              
                // there is probabily a better way to do this.
                setImage(sshot2);
                // getWorld().setPaintOrder(Slingshot.class,Wombat.class); // now draw the slingshot first
                m = Greenfoot.getMouseInfo();
                wombat.setXandY(m.getX(),m.getY());
                mousex = m.getX();
                mousey = m.getY();
                anotherVect.setX(-(getX()-mousex));
                anotherVect.setY(-(getY()-mousey));
                if (anotherVect.magnitude() <= 180) {
                    // the wombat gets loaded at about getY()-14.  The displacement is the difference
                    // between the wombat's position and that location.  
                    displacement.setX(-(getX() - mousex));  // these should be negitive based on how the
                    displacement.setY(-(getY()-60 - mousey)); // coord system is set up, didn't catch that at first
                    //wombat.setXandY(mousex-wombat.getImage().getWidth()/2,mousey+wombat.getImage().getHeight()/2); // move the wombat's location to wherever mousex and mousey is 
                    //if (displacement.magnitude() <= 90) {
                    //wombat.setXandY(mousex-wombat.getImage().getWidth()/2,mousey+wombat.getImage().getHeight()/2);
                    s1.setThePoint(mousex,mousey);
                    s2.setThePoint(mousex,mousey);
                    //}
                    //elasticforce = Phyx.elasticForce(displacement,elasticnum); // so I can see it 
                } else {
                    // set the displacement vector, as well as the wombat, at a length of 90 along the mose position vector
                    displacement.setX(-(getX() - mousex));  
                    displacement.setY(-(getY()-60 - mousey));
                    scalar = (90/anotherVect.magnitude());
                    displacement.scalarMultiply(scalar); // that's the displacement vector....
                    anotherVect.scalarMultiply(scalar);
                   // wombat.setXandY(getX()+anotherVect.getX()-wombat.getImage().getWidth()/2,getY()+anotherVect.getY()+wombat.getImage().getHeight()/2);
                    s1.setThePoint(getX()+anotherVect.getX(),getY()+anotherVect.getY());
                    s2.setThePoint(getX()+anotherVect.getX(),getY()+anotherVect.getY());
                    
                    
                }
            //}  
            // ok this is getting pretty long!  Still not done.  Need a holder for the wombat.  1st step: move the wombat back a couple pixels
            // toward the slingshot.  another vect already points from the slingshot to where th mouse is, so just use that one
            anotherVectUnit = anotherVect.returnUnit();
            anotherVectUnit.scalarMultiply(-5);  // This points from the end of the elastic straps to the slingshot, and is 3 units long
            wombat.setXandY(getX()+anotherVectUnit.getX()+anotherVect.getX()-wombat.getImage().getWidth()/2,
                            getY()+anotherVectUnit.getY()+anotherVect.getY()+wombat.getImage().getHeight()/2);
            anotherVectUnit.scalarMultiply(0.3);
          
        }
    }
    
    
    public void setElasticNum(double input) {
        elasticnum = input;
    }
    
    public void initialize() {
        s1 = new Strap();
        s2 = new Strap();
        t = (WombatWorld) getWorld();
        getWorld().addObject(s1,getX() +9,getY()-23); // the order in which these are added matters
        getWorld().addObject(s2,getX() -6,getY()-23); // when it comes to which gets drawn first
        setImage(sshot1);
        getWorld().setPaintOrder(Wombat.class,Slingshot.class); // draw the wombat on top of the sling shot
        initialized = true;
    }
}
