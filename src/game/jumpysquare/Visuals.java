
package game.jumpysquare;

import collections.LinkedList;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lazyf
 */
public class Visuals extends JPanel{
     int count = 0;//keeps track of first time run
     Graphics g; //graphics for the squares
     public JFrame frame; //legendary frame for the squares 
    /**
     * Constructor. Starts creating graphics 
     */
    public Visuals(){
        System.out.println("Wow I can see");
        frame = new JFrame();
        if(count <=2 ){
            setFrame();
        }
               
    }
    /**
     * paints all the graphics to the parent class(JPanel)
     * @param g the graphics. 
     */
    @Override 
    public void paintComponent(Graphics g) {
        this.g = g;
        super.paintComponent(g);
    }

    private void setFrame() {
        frame.setVisible(true);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
    } 
    /**
     * Delete everything on screen (on panel) 
     * @param rects all the rectangle you want to delete
     */
    protected void clear(LinkedList<Rectangle> rects){
        for (int i = 0; i < rects.size(); i++) {
            Rectangle temp = rects.get(i);
            g.clearRect(temp.x, temp.y, temp.width, temp.height);
        }
        repaint();
    }
    /**
     * Deletes rectangle of your choosing
     * @param rect 
     */
     protected void clear(Rectangle rect){
        g.clearRect ((int)rect.getX(), 
                    (int)rect.getY(),
                    (int)rect.getWidth(), 
                    (int)rect.getHeight());
        repaint();
      
    }

}
