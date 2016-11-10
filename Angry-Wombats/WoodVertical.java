import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodVertical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WoodVertical extends Target
{
    public WoodVertical() {
        super();
    }
    public void act()
    {
       if(hitCount == life-1) {
           setImage("wood2_broken.png");
       }
       super.act();
    }    
}
