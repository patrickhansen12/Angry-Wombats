import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wood here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wood extends Target
{
    public Wood() {
        super();
    }
    /**
     * Act - do whatever the Wood wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
       if(hitCount == life-1) {
           setImage("wood_broken.png");
       }
       super.act();
    }    
}
