package SlingShot;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PelletRect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PelletRect  extends Actor
{
    /**
     * Act - do whatever the PelletRect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }    
    
    public void setPosition(double X, double Y) {
        setLocation((int) X,(int) Y);
    }
    
    public void rotate(double theta) {
        setRotation((int) theta);
    }
}
