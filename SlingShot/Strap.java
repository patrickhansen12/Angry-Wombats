import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * So, this is basically a square.  The square will streach out one of it's corners 
 * to wherever it's told to as long as it's witin 100 pixels, or however big you want
 * to make the image
 * 
 * @author Ben 
 * @version (a version number or a date)
 */

public class Strap extends Actor
{
    /**
     * Act - do whatever the polygon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int[] xpts = new int[4];// the arrays for holding the x and y points
    int[] ypts = new int[4];
    int dx; int dy; // where, in the strap's coordinate system, it gets streached to
    int px; int py; // where the strap gets told to streach to by another object.  Different!
    boolean initialized = false;
    int w = 200; int h = 200; // the total size of the image.  Big!
    int size = 3; // the width of the strap
    boolean streached = false; // are we being streached?
    int thecorner = 3; // which corner of the polygon gets streached out?
    GreenfootImage img = new GreenfootImage(w,h);
    public void act() 
    {
        // Add your action code here.
        img = new GreenfootImage(w,h);
        if (!initialized) {
            initialize();
        }
        
        if (streached) {
            resetCorners();
            dx = px - getX();
            dy = py - getY();
        
            xpts[thecorner] = w/2-size + dx;           ypts[thecorner] = h/2+size + dy;// + 2*size;
            //xpts[4] = w/2-size + dx;           ypts[4] = h/2+size + dy;// - 2*size;
        }
        // draw the thing!
        img.setColor(Color.RED);
        img.fillPolygon(xpts,ypts,4);
        img.setColor(Color.BLACK); // black outline!
        img.drawPolygon(xpts,ypts,4);
        setImage(img);
    }   
    // the strap will streach out to wherever this point is.  Depending on where the point
    // is however, a different corner might need to be used.
    public void setThePoint(double x, double y) {
        px = (int) x;
        py = (int) y;
        streached = true;
        // decide which corner gets streached.  This depends on where px and py are
        if ((getX()-px) < 0) { // to the right of the polygon. 
            if ((getY() - py) < 0) { // we are below.  Strech corner two
                thecorner = 2;
            }else { // streach corener one
                thecorner = 1;
            }
        } else { // to the left
            if ((getY() - py) < 0) { // streach corener 3
                thecorner = 3;
            }else { // corner 0 
                thecorner = 0;
            }
        }
        
    }
    
    public void resetCorners() {
        xpts[0] = w/2-size;
        xpts[1] = w/2 + size;
        xpts[2] = w/2 + size;
        xpts[3] = w/2 - size;
        //xpts[4] = xpts[0];
        ypts[0] = h/2 -size ;
        ypts[1] = h/2 - size;
        ypts[2] = h/2 + size;
        ypts[3] = h/2 +size;
        //ypts[4] = ypts[0];
    }
    
    // just resets everything!!
    public void reset() {
        initialized = false;
    }
    // set up the square. 
    public void initialize() {
        xpts[0] = w/2-size;
        xpts[1] = w/2 + size;
        xpts[2] = w/2 + size;
        xpts[3] = w/2 - size;
        //xpts[4] = xpts[0];
        ypts[0] = h/2 -size ;
        ypts[1] = h/2 - size;
        ypts[2] = h/2 + size;
        ypts[3] = h/2 +size;
        //ypts[4] = ypts[0];
        initialized = true;
        streached = false;
    }
}

