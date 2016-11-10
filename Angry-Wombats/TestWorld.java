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
    boolean wombatadded = false;
    boolean shotloaded = false;
    LinkedList <Wombat> wombats = new LinkedList();
    
    GreenfootSound backgroundMusic = new GreenfootSound("music.wav");
    private boolean musicplaying = false;
 
    double wombatmass = 5;
    boolean paused = false;
    double grav = 0.6; // tell the wombats what gravity is
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

        super(1200, 400, 1); 

        setPaintOrder(Slingshot.class,Wombat.class,Strap.class);

        addWombats();
        addObject(s,130,395 - s.getImage().getHeight()/2);

        prepare();
    }

    public void act() {
      if(!musicplaying){
    
     backgroundMusic.playLoop();
     musicplaying = true;
     
    }
        // added a pause feture, to keep things from getting too mixed up.
        if(!paused) {
            if (!shotloaded) {
                if(!wombats.isEmpty()) {
                    sendToSShot(wombats.element()); // the wombat at the front of the line.  Send it to the shot
                    shotloaded = true;
                } else {
                    endGame();
                }
            }
        }

        // add some targets to shoot at

    }

    public void addWombats() {
        for(int i=0;i<5;i++) {
            Wombat p = new Wombat();
            wombats.add(p);
            addObject(p,5*15-(i)*15,50-p.getImage().getHeight()/2);
        }
        // wombat at index 4 is at the front of the line.  I wish I could use a queue here.. maybe I can
        // move the first wombat to the 
        wombats.element().sendToSShot();
        sendToSShot(wombats.element());
    }

    // puts the wombat at the head of the list into the slingshot.  Also gives the slingshot a reference 
    // to the wombat.
    public void sendToSShot(Wombat p) {
        p.setGravity(grav);
        p.setAirFriction(airfriction);
        p.sendToSShot();
        p.setMass(wombatmass);
        //windVect.scalarMultiply(-1); // for some reason it's all backward.  Just multiply by -1 though
        p.setWind(windVect); 
        if(wombats.isEmpty()) {
            endGame();
        } else {
            s.addWombat(p);
        }
    }

    public void addAnotherWombat() {
        // so remove the wombat at the first part of the list, and add another wombat at the back of the list
        wombats.removeFirst();
        Wombat p = new Wombat(); // make a new wombat
        // addObject(p,0,100-p.getImage().getHeight()/2); // add it to the world..
        // wombats.addLast(p); // add it to the end of the list
        // now move 'em right...
        moveRight();
        shotloaded = false; // and then tell the act() function to load another shot...
    }

    public void moveRight() {
        for (Wombat p: wombats) {
            p.moveRight(1,15);
        }
    }

    /**
     * This method gets input from the inputbox class.  The boxnum indicates how to interpet this input.
     */
    public void getInput(int boxnum, double input) {
        switch (boxnum) {
            case 1: // this is the gravity one. Gravity is a static int in the PHYX class
            // actually ended up changing gravity through the wombat, because it's easier that way 
            // the variable grav gets sent to each wombat as they are loaded into the slingshot
            /*Phyx.setGravity(input); test1 = true; testVect = Phyx.Gravity(); */grav = input;
            break; 
            case 2: // this sets how hard the slingshot accelerates the wombat
            s.setElasticNum(input); test2 = true;
            break;
            case 3: // this sets the mass of the wombat.  
            wombatmass = input;
            break;
            case 4: // this one sets the Air Friction.  Doesn't do anything yet...
            airfriction = input; // now it does!
            break;

        }
    }

    public void endGame() {
        Greenfoot.stop();
    }

    public void Pause() {
        paused = true;
    }

    public void unPause() {
        paused = false;
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Stone stone = new Stone();
        addObject(stone,1161,367);
        stone.setLocation(1169,361);
        Stone stone2 = new Stone();
        addObject(stone2,1075,368);
        stone2.setLocation(1049,360);
        Stone stone3 = new Stone();
        addObject(stone3,929,360);
        Stone stone4 = new Stone();
        addObject(stone4,1050,266);
        Stone stone5 = new Stone();
        addObject(stone5,1151,255);
        Stone stone6 = new Stone();
        addObject(stone6,912,260);
        WoodVertical woodvertical = new WoodVertical();
        addObject(woodvertical,1105,369);
        WoodVertical woodvertical2 = new WoodVertical();
        addObject(woodvertical2,987,362);
        WoodVertical woodvertical3 = new WoodVertical();
        addObject(woodvertical3,971,265);
        WoodVertical woodvertical4 = new WoodVertical();
        addObject(woodvertical4,1004,248);
        Wood wood = new Wood();
        addObject(wood,1146,307);
        Wood wood2 = new Wood();
        addObject(wood2,867,314);
        Wood wood3 = new Wood();
        addObject(wood3,1027,147);
        wood.setLocation(1169,292);
        stone5.setLocation(1166,221);
        woodvertical4.setLocation(1094,220);
        stone4.setLocation(1023,221);
        woodvertical2.setLocation(867,346);
        stone2.setLocation(1031,367);
        stone2.setLocation(1006,366);
        woodvertical.setLocation(1099,364);
        stone2.setLocation(1026,363);
        wood3.setLocation(1023,293);
        woodvertical3.setLocation(951,221);
        stone6.setLocation(878,221);
        wood2.setLocation(877,293);
        stone3.setLocation(879,366);
        woodvertical2.setLocation(954,363);
        stone3.setLocation(878,362);
        Leaf leaf = new Leaf();
        addObject(leaf,958,292);
        leaf.setLocation(952,290);
        leaf.getX();
        leaf.setLocation(1096,286);
        leaf.setLocation(1098,288);
        Leaf leaf2 = new Leaf();
        addObject(leaf2,956,293);
        leaf2.setLocation(950,290);
        Wood wood4 = new Wood();
        addObject(wood4,1031,147);
        wood4.setLocation(1025,144);
        Wood wood5 = new Wood();
        addObject(wood5,1171,154);
        wood5.setLocation(1168,147);
        wood4.setLocation(1025,148);
        wood5.setLocation(1168,148);
        Stone stone7 = new Stone();
        addObject(stone7,954,189);
        Stone stone8 = new Stone();
        addObject(stone8,1114,189);
        wood4.setLocation(956,116);
        stone8.setLocation(1097,193);
        wood5.setLocation(1099,122);
        Stone stone9 = new Stone();
        addObject(stone9,1100,51);
        Stone stone10 = new Stone();
        addObject(stone10,960,52);
        WoodVertical woodvertical5 = new WoodVertical();
        addObject(woodvertical5,1042,57);
        stone7.setLocation(954,193);
        wood4.setLocation(956,126);
        wood5.setLocation(1101,122);
        woodvertical5.setLocation(1031,55);
        woodvertical5.setLocation(1031,50);
    }
}
