import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * leaf. you have to get all of these to win.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leaf extends Target
{
    public Leaf() {
        super(1); //leaf has only one strenght (just eat it)
    }
    public void act() 
    {
        super.act(); //remove if died
    }
    /** Leaf has an other sound, so we have to override the wood crash sound. **/
    @Override
    public void gotHit() {
        Greenfoot.playSound("leaf.wav");
        hitCount++;
    }
}
