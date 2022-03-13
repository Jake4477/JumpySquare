
package game.logic;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author lazyf
 */
public class Player{
    Color color; // to distinguish ai players
    Rectangle player;
    int x;
    int y;
    int yVel;
    boolean dead = false;
    public static int score = 0; // score for ai. 
    public Player(){
         x = 100;
         y = 150;
        yVel = 0;
        player = new Rectangle(x, y, 25, 25);
        color = new Color(random(0, 100),random(0, 100),random(0, 100));
    }
    public void changeYVel(int v){
        yVel -= v;
        int futureValue = y + yVel;
        if(futureValue >=0){
            y += yVel;
        }
        
        player.setBounds(x, y, 25, 25);
    }
    public void setYVel(int yVel){
        this.yVel = yVel;
    }
    public int getYVel(){
        return yVel;
    }
    public void setY(int y){
        this.y = y;
        player.setBounds(x, y, 25, 25);
    }
    public int getY(){
        return y;
    }
     public int getX(){
        return x;
    }
    public Rectangle getPlayer(){
        return player;
    }
    /**
     * Kills the player
     */
    public void die(){
        dead = true;
    }
    /**
     * Bring the dead back to life
     */
    public void reincarnate(){
        dead = false;
    }
    public boolean isAlive(){
        if(dead){
            return false;
        }
        return true;
    }
    
    public Color getColor(){
        return color;
    }

    
     /**
     * Returns int between low and high
     * @param low low number
     * @param high high number
     * @return random number between low and high
     */
    public int random(int low, int high){
        return (int)((high-low+1) * Math.random() + low);
    }
}
