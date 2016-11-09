import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stone extends Target
{
    /**
     * Act - do whatever the Stone wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(this.touch(Wombat.class)) {
            if(hitCount >= life) {
                getWorld().removeObject(this);
            } else {
                setImage("rock_broken.png");
                hitCount++;
            }
        }
    }    
}
