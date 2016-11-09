import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodVertical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WoodVertical extends Target
{
    TestWorld t;
    /**
     * Act - do whatever the WoodVertical wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
       if(currentWombat != null && this.touch(currentWombat)) {
            if(hitCount >= life) {
                getWorld().removeObject(this);
            } else {
                setImage("wood2_broken.png");
                hitCount++;
            }
        }
    }    
}
