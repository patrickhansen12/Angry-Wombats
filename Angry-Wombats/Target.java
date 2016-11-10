import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * This class contains two methods for pixel-perfect collision-detection
 * 
 * @author (Busch2207 (Moritz L.)) 
 * @version (09.11.2013)
 */
public class Target extends Actor
{
    protected int hitCount;
    protected int life;
    
    public Target(int life) {
        this.life = life;
        this.hitCount = 0;
    }
    public Target() {
        this.life = 2;
        this.hitCount = 0;
    }
    public void act() {
        if(hitCount >= life) {
            getWorld().removeObject(this);
        }
    }
    public void gotHit() {
        hitCount++;
    }
    
    public int getHits() {
        return hitCount;
    }
    
    public void increaseHits() {
        hitCount++;
    }
}
