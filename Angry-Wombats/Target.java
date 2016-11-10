import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * This is ours. Just a super for targets.
 * 
 */
public class Target extends Actor
{
    protected int hitCount;
    protected int life;
    
    /** If you want to give your own "strenght" to your targets **/
    public Target(int life) {
        this.life = life;
        this.hitCount = 0;
    }
    /** Default strenght is 2 **/
    public Target() {
        this.life = 2;
        this.hitCount = 0;
    }
    public void act() {
        //just remove if "died"
        if(hitCount >= life) {
            getWorld().removeObject(this);
        }
    }
    /** Basically this is for the wooden obstacles. It plays the crash sound and increases the hit number **/
    public void gotHit() {
        Greenfoot.playSound("crash.wav");
        hitCount++;
    }
    /** Returns an int of how many times the target was hit. **/
    public int getHits() {
        return hitCount;
    }
    /** Increases hits of target. Not used by us (because of protected variables) but useful in the future. **/
    public void increaseHits() {
        hitCount++;
    }
}
