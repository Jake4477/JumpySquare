/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.logic;

import collections.LinkedList;
import java.awt.Rectangle;

/**
 *
 * @author lazyf
 */
public class Collision {

    public Collision() {

    }

    /**
     * Checks if player hits towers
     *
     * @param player player rectangle
     * @param towers tower linked list
     * @return boolean if collision or not
     */
    public boolean checkHit(Rectangle player, LinkedList<Tower> towers) {
        for (int i = 0; i < towers.size(); i++) {
            if (player.intersects(towers.get(i).getBottom())
                    || player.intersects(towers.get(i).getTop()) || player.y >= 550) {
                return true;
            }
        }
        return false;
    }

    
    
    public boolean checkPoint(Rectangle player, LinkedList<Tower> towers) {
            for (int i = 0; i < towers.size(); i++) {
                //if touching point rectangle
                if (player.intersects(towers.get(i).getScore())) {
                    //remove so player can't touch again
                    towers.get(i).getScore().setBounds(0, 0, 0, 0);
                    return true;
                }
            }
          return false; 
    }
    

    /**
     * Checks if any player in array hits any tower
     * @param players The players to check passed in an array
     * @param towers the towers in a linked list
     */
    public void checkHit(Player[] players, LinkedList<Tower> towers) {
        for (int i = 0; i < players.length; i++) {
            if (checkHit(players[i].getPlayer(), towers)) {
                players[i].die(); //kills player if they hit something
            }
        }
        
    }
}
