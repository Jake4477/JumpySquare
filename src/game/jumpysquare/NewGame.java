
package game.jumpysquare;

import collections.LinkedList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import game.logic.Collision;
import game.logic.Player;
import game.logic.Tower;
import nn.NeuralNetwork;

/**
 *
 * @author lazyf
 */
public class NewGame extends Visuals implements KeyListener{
    boolean timerCheck = false; // checks if timer should run
    boolean play = false;
    Graphics g;
    Player player;
    boolean first = true;
    Collision collision = new Collision();
    int target = 5; // increases difficulty at score 10
    int score = 0;
    
    int distance = 350; // distance needed for new tower to spawn
    
    
    int speed = 3; // speed of towers; higher = faster
    int timerSpeed = 20; // speed of timer
    int fallSpeed = -1; //rate of fall
    int jumpHeight = 15; //jump height
    NeuralNetwork nn[];
    
    
    public static LinkedList<Tower> towers = new LinkedList<>();
    public NewGame(){
        frame.addKeyListener(this);
        play = true;
        player = new Player();
        towers.add(new Tower());
        repaint();
        timer();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.gray);
        int height = 150; // height of the bottom layer 
        //set environment
        g2d.setColor(Color.gray);
        //creates the bottom layer "Grass" Layer
        g2d.fillRect(0, frame.getHeight() - height, frame.getWidth(), height);
        for (int i = 0; i < towers.size(); i++) {
            if(towers.get(i).x < -50){
                towers.remove(i); // deletes tower if off screen
            }
            fillRect(towers.get(i).getBottom(), g2d);
            fillRect(towers.get(i).getTop(), g2d);
            g2d.setColor(Color.gray);
        }
        
            //display player if not dead. Also check if dead
            if (!collision.checkHit(player.getPlayer(), towers)) {
                g2d.setColor(Color.black);
                Rectangle irlPlayer = this.player.getPlayer();
                fillRect(irlPlayer, g2d);
            } else {
                die(); // it was meant to happen  
        }
        //generate new tower if conditions are met
        if(towers.getLast().x < frame.getWidth()/2){
            towers.add(new Tower());
        }
       g2d.setColor(Color.black);
       g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
       g2d.drawString(score + "", frame.getWidth()/2 - 25, 60);
    }
   
    public void jump(){
              player.setYVel(0);
              player.changeYVel(jumpHeight); 
    }
  
    public void fillRect(Rectangle rect, Graphics2D g2d){
        g2d.fillRect(rect.x, rect.y, (int)rect.getWidth(), (int)rect.getHeight());
    }
    
    public void die(){
        timerCheck = false;
        player.setY(150);
        player.setYVel(0);
        towers.clear();
        towers.add(new Tower());
        score = 0;
        speed = 3;
        distance = 350;
        target = 5;        
        repaint();
    }
    
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if(score == target){
                    target+=10;
                    speed +=1;
//                    if(distance >= 50){
//                         distance -= 30;
//                    }
                }
                if (timerCheck) {
                    for (int i = 0; i < towers.size(); i++) {
                        towers.get(i).changeX(speed);
                    }
                    if (collision.checkPoint(player.getPlayer(), towers)) {
                        score++;
                    }
                    player.changeYVel(fallSpeed);
                    repaint();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, timerSpeed);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 32) {
                timerCheck = true;
                jump();
            }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
