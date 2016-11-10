import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Write a description of class WombatWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WombatWorld extends World
{
    Slingshot s = new Slingshot();
    boolean wombatadded = false;
    boolean shotloaded = false;
    LinkedList <Wombat> wombats = new LinkedList();
    
    GreenfootSound backgroundMusic = new GreenfootSound("music.wav");
    private boolean musicplaying = false; //just to start it once
    private int wombatNumber = 15; //Max wombats (tries) that you have
    
    //those are not our code
    double wombatmass = 5;
    boolean paused = false;
    
    boolean test1 = false;
    boolean test2 = false;
    double testnum;
    Vect testVect;

    /**
     * Constructor for objects of class WombatWorld.
     * 
     */
    public WombatWorld()
    {    
        // Create a new world with 12000x400 cells with a cell size of 1x1 pixels.

        super(1200, 400, 1); 

        setPaintOrder(Slingshot.class,Wombat.class,Strap.class);

        addWombats();
        addObject(s,130,395 - s.getImage().getHeight()/2); //add slingshot

        prepare();
    }

    public void act() {
        //start the music
        if(!musicplaying) {
             backgroundMusic.playLoop();
             musicplaying = true;
        }
        //check win state first. if you put this below, you will lose anyway because of "else { endGame(false); }"
        if(checkIfWin()) {
            endGame(true);
        }
        //reload slingshot if you have wombats left. (modified code)
        if (!shotloaded) {
            if(!wombats.isEmpty()) {
                sendToSShot(wombats.element()); // the wombat at the front of the line.  Send it to the shot
                shotloaded = true;
            } else {
                endGame(false); //lose
            }
        }
    }

    /** Adds as many wombats as definied to the world and sends the first in the slingshot.  **/
    public void addWombats() {
        for(int i=0;i<wombatNumber;i++) {
            Wombat p = new Wombat();
            wombats.add(p);
            addObject(p,75,50-p.getImage().getHeight()/2);
        }
        wombats.element().sendToSShot();
        sendToSShot(wombats.element());
    }

    // puts the wombat at the head of the list into the slingshot.  Also gives the slingshot a reference 
    // to the wombat.
    /** Modified code. Sends next wombat in the slingshot if you have more left. If not, you will lose **/
    public void sendToSShot(Wombat p) {
        p.sendToSShot();
        wombatNumber--;
        if(wombats.isEmpty()) {
            endGame(false);
        } else {
            s.addWombat(p);
        }
    }

    //not used, we turned off unlimited wombats.
    /** Adds one wombat to the end of the list and moves all the others to right. (Not used by us) **/
    public void addAnotherWombat() {
        // so remove the wombat at the first part of the list, and add another wombat at the back of the list
        wombats.removeFirst();
        Wombat p = new Wombat(); // make a new wombat
        addObject(p,0,100-p.getImage().getHeight()/2); // add it to the world..
        wombats.addLast(p); // add it to the end of the list
        // now move 'em right...
        moveRight();
        shotloaded = false; // and then tell the act() function to load another shot...
    }
    
    public void reload() {
        wombats.removeFirst();
        shotloaded = false;
    }
    
    //not used
    /** This moves the wombats to the right (Not used by us) **/
    public void moveRight() {
        for (Wombat p: wombats) {
            p.moveRight(1,15);
        }
    }
    
    //not our code
    /**
     * This method gets input from the inputbox class.  The boxnum indicates how to interpet this input.
     */
    public void getInput(int boxnum, double input) {
        switch (boxnum) {
            case 1: // this is the gravity one. Gravity is a static int in the PHYX class
            // actually ended up changing gravity through the wombat, because it's easier that way 
            // the variable grav gets sent to each wombat as they are loaded into the slingshot
            /*Phyx.setGravity(input); test1 = true; testVect = Phyx.Gravity(); */
            break; 
            case 2: // this sets how hard the slingshot accelerates the wombat
            s.setElasticNum(input); test2 = true;
            break;
            case 3: // this sets the mass of the wombat.  
            wombatmass = input;
            break;
          

        }
    }

    //our code. end the game with only two possible states: win or lose
    /**Ends the game and makes you win or lose. true parameter will win, false will lose **/
    public void endGame(boolean win) {
        if(win) {
            removeObjects(getObjects(null));
            setBackground("winner.png");
            backgroundMusic.stop();
            Greenfoot.playSound("winner.wav");
            Greenfoot.stop();
        }
        if(!win) {
            removeObjects(getObjects(null));
            setBackground("gameover.png");
            backgroundMusic.stop();
            Greenfoot.playSound("gameover.wav");
            Greenfoot.stop();
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        WombatCounter count = new WombatCounter();
        addObject(count,240,30);
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
        removeObject(leaf2);
        Leaf leaf3 = new Leaf();
        addObject(leaf3,1026,132);
        leaf3.setLocation(1027,129);
    }
    
    //our code. checks if you ate all the leaves so you can win if it's true.
    /** Check if player won. Returns true if player ate all the leaves. You can use this with endGame() **/
    public boolean checkIfWin() {
        if(getObjects(Leaf.class).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}







