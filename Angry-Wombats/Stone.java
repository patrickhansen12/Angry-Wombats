import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stone extends Target
{
    public Stone() {
        super();
    }
    
    public void act() 
    {
        if(hitCount == life-1) {
            setImage("rock_broken.png");
        }
        super.act();
    }    
}
