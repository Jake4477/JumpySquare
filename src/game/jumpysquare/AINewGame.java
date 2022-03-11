
package game.jumpysquare;

import collections.LinkedList;
import game.logic.Collision;
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

/**
 *
 * @author lazyf
 */
public class AINewGame extends Visuals{
    //Hello
    boolean timerCheck = false; // checks if timer should run
    int generation = 0; // keeps track of ai generation
    int round = 0; // keeps track of rounds ai played
    boolean play = false;
    Graphics g;
    NeuralNetwork nnPlayers[];
    boolean first = true;
    Collision collision = new Collision();
    int target = 5; // increases difficulty at score 10
    int score = 0;
    
    int distance = 350; // distance needed for new tower to spawn
    
    public static final int amount = 200;
    public static final int inputs = 2;
    
    int speed = 3; // speed of towers; higher = faster
    int timerSpeed = 20; // speed of timer
    int fallSpeed = -1; //rate of fall
    int jumpHeight = 15; //jump height
    Genetics genetics = new Genetics();
    
    public static LinkedList<Tower> towers = new LinkedList<>();
    public AINewGame(){
        System.out.println("");
        nnPlayers = new NeuralNetwork[amount]; // number of ai's to train
        //if play was pressed play = true;
        //if watch was pressed play = false;
        play = true;
        timerCheck = true;
        for (int i = 0; i < nnPlayers.length; i++) {
            nnPlayers[i] = new NeuralNetwork(inputs, 3); // creates AI's
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
       g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
       g2d.drawString(generation + "", frame.getWidth()/2 - 25, 60);
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
        
        for (int i = 0; i < nnPlayers.length; i++) {
            if(nnPlayers[i].isAlive()) {
               // System.out.println(nnPlayers[i].getY());
                if(nnPlayers[i].getY() > 30){
                   nnPlayers[i].changeFitness(1);
                  // System.out.println("GOOOD");
                }
                else{
                    
                     nnPlayers[i].changeFitness(-1);
                }
                  
            };
        }
         for (int i = 0; i < nnPlayers.length; i++) {
            if(nnPlayers[i].isAlive()) {
                return false;
            };
        }
     
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
           
            nnPlayers = genetics.crossing(best);
            for (int i = 0; i < nnPlayers.length; i++) {
                //System.out.println(nnPlayers[i].getHidden(0));
                
                nnPlayers[i].setFitness(0);
            }
       }
        round++;
        
        for (int i = 0; i < nnPlayers.length; i++) {
            nnPlayers[i].reincarnate();
            nnPlayers[i].setY(150);
            nnPlayers[i].setYVel(0);
            
        }
        towers.clear();
        towers.add(new Tower());
        score = 0;
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
                if (score == target) {
                    target += 10;
                    //speed += 1;
                }
                if (timerCheck) {
                    for (int i = 0; i < towers.size(); i++) {
                        towers.get(i).changeX(speed);
                    }
                    for (int i = 0; i < nnPlayers.length; i++) {
                        if(nnPlayers[i].isAlive()){
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
            int distance = 0;
            if(nnPlayers[i].getY() < temp.getBottom().y){
                distance = temp.getBottom().y - nnPlayers[i].getY();
            }
            else if(nnPlayers[i].getY() > temp.getBottom().getY()){
                 distance =nnPlayers[i].getY() - temp.getBottom().y;
            }
            double inputs[] = {
                distance/5,
                nnPlayers[i].getYVel()/5
            };
//            System.out.print("[");
//            for (int j = 0; j < inputs.length; j++) {
//                System.out.print(inputs[j] + ",");
//            }
//            System.out.print("]");
//            System.out.println("");
          //  System.out.println(distance);
          
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
