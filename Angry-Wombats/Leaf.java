import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Leaf here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leaf extends Target
{
    public Leaf() {
        super(1);
    }
    public void act() 
    {
        super.act();
    }
    @Override
    public void gotHit() {
        Greenfoot.playSound("leaf.wav");
        hitCount++;
    }
}
