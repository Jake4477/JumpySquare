/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nn.math;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author lazyf
 */
public class Random {
    
           /**
     * Returns int between low and high
     *
     * @param low low number
     * @param high high number
     * @return random number between low and high
     */
    public static int randomInt(int high, int low) {
        return (int) ((high - low + 1) * Math.random() + low);
    }
    
     public static double randomDouble(int low, int high){
        return ThreadLocalRandom.current().nextDouble(low, high);
    }
}
