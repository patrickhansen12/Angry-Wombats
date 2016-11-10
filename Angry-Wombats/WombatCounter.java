import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;

/**
 * This whole thing is ours. Just counts and displays the wombats left. Minus the one which is already loaded.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WombatCounter extends Actor
{
    /**
     * Act - do whatever the WombatCounter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public WombatCounter() {
        //get rid of "greenfoot.png"
        GreenfootImage starterimage = new GreenfootImage(1,1);
        setImage(starterimage); 
    }
    
    public void act() 
    {
     setImage(new GreenfootImage(250,150));
     GreenfootImage counter = getImage();
     counter.setFont(new Font("Helvetica",Font.PLAIN,20));
     counter.setColor(Color.WHITE);
     int left = getWorld().getObjects(Wombat.class).size()-1;
     if(left < 0) { left = 0; } //avoid negative values
     counter.drawString(left + " Wombats left", 0,80);
     setImage(counter);
    }    
}
