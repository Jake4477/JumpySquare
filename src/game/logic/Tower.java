
package game.logic;

import java.awt.Rectangle;

/**
 *
 * @author lazyf
 */
public class Tower{
    //generates bottom of piller then creates top with gap from bottom
    public int x = 700;
    int gap = 200; //space between top and bottom 
    private int height;
    Rectangle bottom;
    Rectangle top;
     Rectangle score;
   
    
    public Tower(){
        //generate at end if screen
        bottom = new Rectangle();
        top = new Rectangle();
        score = new Rectangle();//used to see if player gets a point
        height = random(1, 300);
        bottom.setBounds(x, 550 - height, 50, height);
        top.setBounds(x, 0, 50, 550-height-gap);
        score.setBounds(x, (int)bottom.getY() - gap, 50, gap);
    }
    public Rectangle getScore(){
        return score;
    }
    public void changeX(int cX){
        x -= cX;
        bottom.setLocation(x, bottom.y);
        top.setLocation(x, top.y);
        score.setLocation(x, bottom.y - gap);
    }
    public Rectangle getBottom(){
        return bottom;
    }
    public Rectangle getTop(){
        return top;
    }

    public int random(int low, int max) {
        int high = max;
        double seed = Math.random();
        double number = (high - low + 1) * seed + low;
        int randomNumber = (int) number;
        return (randomNumber);
    }
}
