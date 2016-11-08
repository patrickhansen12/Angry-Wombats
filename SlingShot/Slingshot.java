import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slingshot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slingshot  extends Actor
{
    // the images of the slingshot!!
    GreenfootImage sshot1 = new GreenfootImage("Sshotwstrap.gif");
    GreenfootImage sshot2 = new GreenfootImage("Sshot.gif");

    Pellet pellet; // the pellet that gets shot
    MouseInfo m; // need the mouse info....
    Strap s1, s2; // the straps of the slingshot
    PelletRect pr = new PelletRect();
    Vect displacement = new Vect(0,0); // how far the straps get pulled
    Vect elasticforce = new Vect(0,0); // the force the slingshot puts on the pellet
    Vect velocity = new Vect(0,0); // the final velocity of the pellet before it gets launched
    Vect tempVect = new Vect(0,0); // a temp vect for comparison purposes..see shootpellet
    Vect anotherVect = new Vect(0,0);  // another vector.  This one is to make sure the straps don't get pulled too far.
    Vect anotherVectUnit = new Vect(0,0); // the unit vector of another vect
    TestWorld t; // a pointer to the testworld
    double pelletmass = 0;
    
    // other variables
    boolean initialized = false;
    boolean loaded = false;
    boolean firing = false;
    boolean fired = false;
    boolean pelletrectadded = false;
    boolean pelletrectremoved = false;
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
        // Add your action code here.
        if (!initialized) {
            initialize();
        }
        // adding a pellet to the sling shot makes it loaded
        if (loaded) {
            doLoadedStuff(); // I do loaded stuff too, when I drink too much!
            if (Greenfoot.mouseClicked(null)) {
                // whoops, we are not loaded anymore!  Wish it was that easy in real life!
                loaded = false;
                firing = true;
            }
        } 
        
        if (firing) {
            shootPellet(); // shoot the dern thing 
        }
    }   
    
    public void shootPellet() {
        // so, accelerate toward where the pellet got loaded, until the pellet passes that
        // point.  Get the pellet to move, then tell the straps to follow the pellet.
        // first, get the Force on the pellet.
        elasticforce = Phyx.elasticForce(displacement,elasticnum);
        // add the force to the velocity.  Actually I supppose the "correct" way to do this is add the Force/Mass
        pelletmass = pellet.getMass();
        velocity = Phyx.getVelocity(velocity,elasticforce,pelletmass); // this method can take mass into account
        // now that velocity has been computed, we know how fast the pellet is moving.
        // add the velocity to the displacement, and send the pellet to that new location.
        // also, move the straps.  Store the displacement before we alter it.
        tempVect.setEqualTo(displacement); // store the displacement variables here
        displacement.add(velocity);
        //pellet.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        s1.setThePoint(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        s2.setThePoint(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        // I tried to make the pellet rect move with the pellet, but greenfoot draws them at different times, so it didn't work.
        // just get rid of the pellet rect for now.
        if  (!pelletrectremoved) {
            getWorld().removeObject(pr);
            pelletrectremoved = true;
        }
        pellet.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        // next we see if the pellet has passed the point where it was fired.  The pellet can be fired from
        // any direction.  Now when fireing, the displacement of the pellet is getting smaller.
        // if the displacement starts getting bigger, we know we should stop firing.
        // the displacement was stored in tempVect, so use that to compare.
        pellet.setXandY(getX() + displacement.getX(),(getY()-14) + displacement.getY());
        if (tempVect.magnitude() <= displacement.magnitude()) {
            pellet.release(velocity); // release the pellet at the velocity..
            // now that the pellet has been released, time to erase the old dots..
          
            firing = false; // stop manipulating the pellet...
            // now reset everything!
            s1.reset();
            s2.reset(); // all the booleans should already be reset, so just the straps
            velocity.setX(0); velocity.setY(0); // set all the vectors back to zero
            displacement.setX(0); displacement.setY(0);
            elasticforce.setX(0); elasticforce.setY(0);
            setImage(sshot1); // set the image back..
            getWorld().setPaintOrder(Pellet.class,Slingshot.class,PelletRect.class); // now set the draw order don't forget the dial!
            pelletrectadded = false;
            pelletrectremoved = false;
        }
        
    }
    // this endes up giving control of the pellet to the slingshot, once it gets loaded
    public void addPellet(Pellet p) {
        pellet = p;
        loaded = true;
        
    }
    
    public void doLoadedStuff()
    {
        // alright.  we should make sure the mouse is in the area of the slingshot
        //if (mouseInNeighborhood()) {
        //if (!pelletrectadded) {
        //    pelletrectadded = true;
        //    getWorld().addObject(pr,0,0);
        //   getWorld().setPaintOrder(Slingshot.class, PelletRect.class, Pellet.class);
        //}
            if (Greenfoot.mouseDragged(null)) {
                if (!pelletrectadded) {
                    pelletrectadded = true;
                    getWorld().addObject(pr,0,0);
                    getWorld().setPaintOrder(Slingshot.class, PelletRect.class, Pellet.class);
                }
                // there is probabily a better way to do this.
                setImage(sshot2);
                // getWorld().setPaintOrder(Slingshot.class,Pellet.class); // now draw the slingshot first
                m = Greenfoot.getMouseInfo();
                pellet.setXandY(m.getX(),m.getY());
                mousex = m.getX();
                mousey = m.getY();
                anotherVect.setX(-(getX()-mousex));
                anotherVect.setY(-(getY()-mousey));
                if (anotherVect.magnitude() <= 90) {
                    // the pellet gets loaded at about getY()-14.  The displacement is the difference
                    // between the pellet's position and that location.  
                    displacement.setX(-(getX() - mousex));  // these should be negitive based on how the
                    displacement.setY(-(getY()-14 - mousey)); // coord system is set up, didn't catch that at first
                    //pellet.setXandY(mousex-pellet.getImage().getWidth()/2,mousey+pellet.getImage().getHeight()/2); // move the pellet's location to wherever mousex and mousey is 
                    //if (displacement.magnitude() <= 90) {
                    //pellet.setXandY(mousex-pellet.getImage().getWidth()/2,mousey+pellet.getImage().getHeight()/2);
                    s1.setThePoint(mousex,mousey);
                    s2.setThePoint(mousex,mousey);
                    //}
                    //elasticforce = Phyx.elasticForce(displacement,elasticnum); // so I can see it 
                } else {
                    // set the displacement vector, as well as the pellet, at a length of 90 along the mose position vector
                    displacement.setX(-(getX() - mousex));  
                    displacement.setY(-(getY()-14 - mousey));
                    scalar = (90/anotherVect.magnitude());
                    displacement.scalarMultiply(scalar); // that's the displacement vector....
                    anotherVect.scalarMultiply(scalar);
                   // pellet.setXandY(getX()+anotherVect.getX()-pellet.getImage().getWidth()/2,getY()+anotherVect.getY()+pellet.getImage().getHeight()/2);
                    s1.setThePoint(getX()+anotherVect.getX(),getY()+anotherVect.getY());
                    s2.setThePoint(getX()+anotherVect.getX(),getY()+anotherVect.getY());
                    
                    
                }
            //}  
            // ok this is getting pretty long!  Still not done.  Need a holder for the pellet.  1st step: move the pellet back a couple pixels
            // toward the slingshot.  another vect already points from the slingshot to where th mouse is, so just use that one
            anotherVectUnit = anotherVect.returnUnit();
            anotherVectUnit.scalarMultiply(-5);  // This points from the end of the elastic straps to the slingshot, and is 3 units long
            pellet.setXandY(getX()+anotherVectUnit.getX()+anotherVect.getX()-pellet.getImage().getWidth()/2,
                            getY()+anotherVectUnit.getY()+anotherVect.getY()+pellet.getImage().getHeight()/2);
            anotherVectUnit.scalarMultiply(0.3);
            pr.setPosition(getX()+anotherVectUnit.getX()+anotherVect.getX()-pellet.getImage().getWidth()/2,
                            getY()+anotherVectUnit.getY()+anotherVect.getY()+pellet.getImage().getHeight()/2);
            pr.rotate(-anotherVect.getAngle());
        }
    }
    /*
     * ended up not needing this.  Still might use if for later
    public boolean mouseInNeighborhood() {
        if (Greenfoot.mouseMoved(null)) {
            m = Greenfoot.getMouseInfo();
            mousex = m.getX(); mousey = m.getY();
        }
        // Think I'll make the range 150 or so
        if ((Math.abs(getX()-mousex) < 150) && (Math.abs(getY()-mousey) < 150)) {
            return true;
        } else {
            return false;
        }
    }
    */
   
    public void setElasticNum(double input) {
        elasticnum = input;
    }
    
    public void initialize() {
        s1 = new Strap();
        s2 = new Strap();
        t = (TestWorld) getWorld();
        getWorld().addObject(s1,getX() +7,getY()-17); // the order in which these are added matters
        getWorld().addObject(s2,getX() -8,getY()-17); // when it comes to which gets drawn first
        setImage(sshot1);
        getWorld().setPaintOrder(Pellet.class,Slingshot.class); // draw the pellet on top of the sling shot
        initialized = true;
    }
}
