 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Write a description of class TestWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestWorld  extends World
{
    Slingshot s = new Slingshot();
    boolean pelletadded = false;
    boolean shotloaded = false;
    LinkedList <Pellet> pellets = new LinkedList();
 
    double pelletmass = 5;
    boolean paused = false;
    double grav = 0.6; // tell the pellets what gravity is
    double airfriction = 0.1;
    Vect windVect = new Vect(0,0);
    // testing variables
    boolean test1 = false;
    boolean test2 = false;
    double testnum;
    Vect testVect;
    
    /**
     * Constructor for objects of class TestWorld.
     * 
     */
    public TestWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        
        super(960, 540, 1);
        //GreenfootImage bg = new GreenfootImage("bg.jpg");
        //setBackground(bg);
        setPaintOrder(Slingshot.class,Pellet.class,Strap.class);
        addPellets();
        addObject(s,130,350 - s.getImage().getHeight()/2);
       
    }
    
    public void act() {
        
        // added a pause feture, to keep things from getting too mixed up.
        if(!paused) {
            if (!shotloaded) {
                sendToSShot(pellets.element()); // the pellet at the front of the line.  Send it to the shot
                shotloaded = true;
            }
        }
        
        // add some targets to shoot at
       
    }
    
    public void addPellets() {
        for (int i = 0 ; i < 5; i++) {
            Pellet p = new Pellet();
            pellets.add(p);
            addObject(p,5*15-(i)*15,350-p.getImage().getHeight()/2);
        }
        // pellet at index 4 is at the front of the line.  I wish I could use a queue here.. maybe I can
        // move the first pellet to the 
        pellets.element().sendToSShot();
        sendToSShot(pellets.element());
    }
    
    // puts the pellet at the head of the list into the slingshot.  Also gives the slingshot a reference 
    // to the pellet.
    public void sendToSShot(Pellet p) {
        p.setGravity(grav);
        p.setAirFriction(airfriction);
        p.sendToSShot();
        p.setMass(pelletmass);
        //windVect.scalarMultiply(-1); // for some reason it's all backward.  Just multiply by -1 though
        p.setWind(windVect); 
        s.addPellet(p);
    }
    
    public void addAnotherPellet() {
        // so remove the pellet at the first part of the list, and add another pellet at the back of the list
        pellets.removeFirst();
        Pellet p = new Pellet(); // make a new pellet
        addObject(p,0,350-p.getImage().getHeight()/2); // add it to the world..
        pellets.addLast(p); // add it to the end of the list
        // now move 'em right...
        moveRight();
        shotloaded = false; // and then tell the act() function to load another shot...
    }
    
    public void moveRight() {
        for (Pellet p: pellets) {
            p.moveRight(1,15);
        }
    }
    /**
     * This method gets input from the inputbox class.  The boxnum indicates how to interpet this input.
     */
    public void getInput(int boxnum, double input) {
        switch (boxnum) {
            case 1: // this is the gravity one. Gravity is a static int in the PHYX class
            // actually ended up changing gravity through the pellet, because it's easier that way 
            // the variable grav gets sent to each pellet as they are loaded into the slingshot
            /*Phyx.setGravity(input); test1 = true; testVect = Phyx.Gravity(); */grav = input;
            break; 
            case 2: // this sets how hard the slingshot accelerates the pellet
            s.setElasticNum(input); test2 = true;
            break;
            case 3: // this sets the mass of the pellet.  
            pelletmass = input;
            break;
            case 4: // this one sets the Air Friction.  Doesn't do anything yet...
            airfriction = input; // now it does!
            break;
        
           
        }
    }
   
    
    
    public void Pause() {
        paused = true;
    }
    
    public void unPause() {
        paused = false;
    }
    
  
}
