import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Horizontal Wood obstacle
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WoodVertical extends Target
{
    public WoodVertical() {
        super(); //init
    }
    public void act()
    {
       if(hitCount == life-1) {
           setImage("wood2_broken.png"); //chg image 
       }
       super.act(); //remove if "died"
    }    
}
