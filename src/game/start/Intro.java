/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.start;

import game.jumpysquare.AINewGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import game.jumpysquare.NewGame;
import game.jumpysquare.Visuals;

/**
 *
 * @author lazyf
 */
public class Intro extends Visuals{
    int angle = 0; //value that turns the graphics
    boolean done = false; //to determine when name should be displayed
    int length = 50; // length and width
    int width = 50;
    int roundCounter = 0; //following position of rectangle
    Rectangle spinnySquare; // the one and only spinny square
    int squareX = 0; //x value
    int squareY = 0; // y value
    boolean showPlay = false;
    
    Font font; // generally used font

    public Intro() {
        font = new Font("TimesRoman", Font.PLAIN, 50);
        this.setLayout(null);
        System.out.println("intro");
        spinnySquare = new Rectangle();
        timer();
    }

    public void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                loop();
            }

        }, 0, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
       

         
         if(!done){
       ///https://stackoverflow.com/questions/18860700/rotating-the-rectangle-object-in-java
        g2d.translate(spinnySquare.x + spinnySquare.getWidth()/2, spinnySquare.y + spinnySquare.getHeight() / 2);
        g2d.rotate(angle * Math.PI / 360);
        g2d.translate(-spinnySquare.x - spinnySquare.getWidth()/2, -spinnySquare.y - spinnySquare.getHeight() / 2);
        // end of stack overflow code
        }
         else {
            g2d.drawString("Jumpy Square!", 175, 150);
        }
        g2d.fill(spinnySquare);
        
        //this.clear(spinnySquare);
    }
    
   
    public void loop() {
        //Changes the vertical Position of the square///////////////////////
        if (spinnySquare.y < 350) {
            spinnySquare.y += 5;
        }
        //Changes the horizontal position of the Square/////////////////////
        if (roundCounter == 0) {
            if (spinnySquare.x <= frame.getWidth()) {
                spinnySquare.x += 25;
            } else {
                roundCounter++;
            }
        } else if (roundCounter == 1) {
            if (spinnySquare.x >= 0) {
                spinnySquare.x -= 25;
            } else {
                roundCounter++;
            }
        } else if (roundCounter == 2) {
            
            if(spinnySquare.x < 135){
                spinnySquare.x += 10;
            }
            else{
                makeButton();
                done = true;
                roundCounter++;
            }
            
          
        }
        //Changes the size of the square////////////////////////////////////
        if (spinnySquare.width < 400) {
            spinnySquare.width += 5;
            spinnySquare.height += 5;
        }
        //puts square on center of screen//////////////////////////////////
        
        
             angle+=7;
        
       //Animation Complete////////////////////////////////////////////////
        repaint(); // repaints the canvas to apply changes
    }

    private void makeButton() {
        ////////////Code for play button///////////////
        JButton play = new JButton("Play");
        JButton watch = new JButton("Watch");
        int [] playD = {150, 50, 150, 70};
        int [] watchD = {110, 150, 230, 70};
        setButton(play, Color.red, playD);
       setButton(watch,Color.lightGray,watchD);
    }
    /**
     * Creates buttons to avoid repetitive code
     * @param button the button to create on spinnySquare rectangle
     * @param dimensions int array [x, y, width, height]
     */
    public void setButton(JButton button, Color color, int dimensions[]){
        button.setBounds(dimensions[0] + (int)spinnySquare.getWidth()/2 -75,
                spinnySquare.y + dimensions[1], dimensions[2], dimensions[3]);
        button.setBackground(color);
        button.setFont(font); // slows program down
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(button.getText().equals("Play")){
                    new NewGame(); //new game if play is clicked
                }
                else{
                    new AINewGame();//new game if Watch is clicked
                }
                System.gc();
            }
        });
        this.add(button);
    }


}
