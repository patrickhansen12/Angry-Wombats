import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Stone obstacle
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stone extends Target
{
    public Stone() {
        super(); //init super's variables
    }
    
    public void act() 
    {
        if(hitCount == life-1) {
            setImage("rock_broken.png"); //change image
        }
        super.act(); //remove if "died"
    }    
}
