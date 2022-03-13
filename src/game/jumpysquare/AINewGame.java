
package game.jumpysquare;

import collections.LinkedList;
import static game.jumpysquare.NewGame.towers;
import game.logic.Collision;
import game.logic.Player;
import game.logic.Tower;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import nn.NeuralNetwork;
import nn.genetics.Genetics;
import nn.math.SearchAndSort;

/**
 *
 * @author lazyf
 */
public class AINewGame extends Visuals{
    boolean timerCheck = false; // checks if timer should run
    int generation = 0; // keeps track of ai generation
    int round = 0; // keeps track of rounds ai played
    boolean play = false;
    Graphics g;
    NeuralNetwork nnPlayers[];
    boolean first = true;
    Collision collision = new Collision();
    int target = 5; // increases difficulty at score 10
    int alive = 0;
    
    
    int distance = 350; // distance needed for new tower to spawn
    
    public static final int amount = 200;
    public static final int inputs = 3;
    
    int speed = 3; // speed of towers; higher = faster
    int timerSpeed = 20; // speed of timer
    int fallSpeed = -1; //rate of fall
    int jumpHeight = 15; //jump height
    Genetics genetics = new Genetics();
    
    
    public int x1Draw = 0;
    public int x2Draw = 0;
    public static LinkedList<Tower> towers = new LinkedList<>();
    public AINewGame(){
        nnPlayers = new NeuralNetwork[amount]; // number of ai's to train
        //if play was pressed play = true;
        //if watch was pressed play = false;
        play = true;
        timerCheck = true;
        for (int i = 0; i < nnPlayers.length; i++) {
            nnPlayers[i] = new NeuralNetwork(inputs, 4); // creates AI's
        }  
        towers.add(new Tower());
        repaint();
        timer();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.gray);
        
        g2d.drawLine(25, x1Draw, 25, x2Draw);
        
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
            for (int i = 0; i < nnPlayers.length; i++) {
                collision.checkHit(nnPlayers, towers);
                if(nnPlayers[i].isAlive()){
                    g2d.setColor(nnPlayers[i].getColor());
                    fillRect(nnPlayers[i].getPlayer(), g2d);
                }
            }
        //generate new tower if conditions are met
        if(towers.getLast().x < frame.getWidth()/2){
            towers.add(new Tower());
        }
       g2d.setColor(Color.black);
       g2d.setFont(new Font("TimesRoman", Font.PLAIN, 25));
       g2d.drawString("Generation: " + generation, frame.getWidth()/2, 60);
       g2d.drawString("Alive: " + alive, frame.getWidth()-150, 60);
       g2d.drawString("Score: " + Player.score, 5, 60);
    }
   
    public void jump(int i){
        nnPlayers[i].setYVel(0);
        nnPlayers[i].changeYVel(jumpHeight);
    }
    
    public void fillRect(Rectangle rect, Graphics2D g2d){
        g2d.fillRect(rect.x, rect.y, (int)rect.getWidth(), (int)rect.getHeight());
    }
    
    ///////////////Change for AI Logic///////////////////////////////
    public boolean checkDeath(){
        int numberAlive = 0;
        for (int i = 0; i < nnPlayers.length; i++) {
            
            if(nnPlayers[i].isAlive()) {
                numberAlive++;
               // System.out.println(nnPlayers[i].getY());
                nnPlayers[i].changeFitness(1);
            }
        }
        alive = numberAlive;
         for (int i = 0; i < nnPlayers.length; i++) {
            if(nnPlayers[i].isAlive()) {
                return false;
            };
        }
        alive = 0;
     
        //check round and train if neccassary 
        if(round==3){
            round = 0;
            generation++;
            NeuralNetwork best[];
//            for (int i = 0; i < nnPlayers.length; i++) {
//                System.out.print(nnPlayers[i].getFitness() + ",");
//            }
            best = genetics.fitness(nnPlayers);
//            for (int i = 0; i < best.length; i++) {
//                System.out.print(best[i].getFitness() + ",");
//            }
//            System.out.println(best[1]);

            //gets the top two players and breeds them
           
            nnPlayers = genetics.crossing(best).clone();
            for (int i = 0; i < nnPlayers.length; i++) {
                nnPlayers[i].setFitness(0);
            }
       }
        round++;
        
        for (int i = 0; i < nnPlayers.length; i++) {
            nnPlayers[i].reincarnate();
            nnPlayers[i].setY(150);
            nnPlayers[i].setYVel(0);
            System.out.println("RESET SCORE");
            Player.score = 0;
            
        }
        towers.clear();
        towers.add(new Tower());
        speed = 3;
        distance = 350;
        target = 5;
        repaint();
        return true;
    }
    
    public void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                    aILoop();
                    System.out.println(Player.score);
                if (Player.score == target) {
                    target += 10;
                    speed += 1;
                }
                if (timerCheck) {
                    for (int i = 0; i < towers.size(); i++) {
                        towers.get(i).changeX(speed);
                    }
                    for (int i = 0; i < nnPlayers.length; i++) {
                        if (nnPlayers[i].isAlive()) {
                            //checking if the ai scores
                            if (collision.checkPoint(nnPlayers[i].getPlayer(), towers)) {
                                System.out.println("It happened");
                                Player.score++;
                                System.out.println(Player.score);
                            }
                            nnPlayers[i].changeYVel(fallSpeed);
                        }
                    }
                    repaint();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, timerSpeed);
    }

    private void aILoop() {
        //becuse when score is hit z is set to 0 I can use that to 
        //find the distance to the next tower
        //Feed forward for every network
        checkDeath();
        int nextTowerX = 0;
        Tower temp = new Tower();
        for (int i = 0; i < towers.size(); i++) {
            if(towers.get(i).getScore().getX() != 0){
                nextTowerX = (int)towers.get(i).getScore().getX();
                temp = towers.get(i);
                i = towers.size() + 1;
            }
        }
        for (int i = 0; i < nnPlayers.length; i++) {
            int yDistance = 0;
            int xDistance = 0;
            //distance = Square root((y2 - y1)^2)
            
            yDistance = nnPlayers[i].getY() - (temp.getBottom().y - 100);
            xDistance = nnPlayers[i].getX() - (temp.getBottom().x);
            if(xDistance < 0) xDistance *= -1;
            
          //  if(distance < 0) distance *= -1;
            
            
            if(nnPlayers[i].getY() < (temp.getBottom().y + 100)){
//                x1Draw = nnPlayers[i].getY();
//                distance = temp.getBottom().y - (nnPlayers[i].getY() + 100);
               // x2Draw = nnPlayers[i].getY() + distance;
            }
            else if(nnPlayers[i].getY() > (temp.getBottom().getY() - 100)){
//                 distance =nnPlayers[i].getY() - (temp.getBottom().y - 100);
//                 x1Draw = nnPlayers[i].getY();
                 // x2Draw = nnPlayers[i].getY() - distance;
            }
            //inputs to feed forward.
            System.out.println(temp.getBottom().x);
            double inputs[] = {
                speed / 5,
                yDistance / 5,
                nnPlayers[i].getYVel()/ 5
            };
            //System.out.println(distance);
          
            double output = nnPlayers[i].feedForward(inputs);
//            for (int j = 0; j < nnPlayers[0].getHidden().length; j++) {
//                System.out.println(nnPlayers[0].getBias(j));
//            }
          //  System.out.println("out " + output);
            if (output > 0.55) {
                jump(i);
            }
        }
    }
    
    public NeuralNetwork[] getPlayers(){
        return nnPlayers;
    }
}
