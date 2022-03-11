/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nn.math;

import nn.NeuralNetwork;

/**
 *
 * @author lazyf
 */
public class SearchAndSort {
    public SearchAndSort(){
        
    }
    
        public static int[] bubbleSort(int [] values){
        int[] copy = values.clone();
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length - 1; j++) {
                if (copy[j] < copy[j+1]) {
                    // Swap them (by making a temporary variable)
                    int swap = copy[j];
                    copy[j]     = copy[j+1];
                    copy[j+1]   = swap;
               }         
            }
        }
        
        return copy;
    }
        
        public static NeuralNetwork[] bubbleSortNN(NeuralNetwork [] nn){
        NeuralNetwork[] copy = nn.clone();
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length - 1; j++) {
                if (copy[j].getFitness() < copy[j+1].getFitness()) {
                    // Swap them (by making a temporary variable)
                    NeuralNetwork swap = copy[j];
                    copy[j]     = copy[j+1];
                    copy[j+1]   = swap;
               }         
            }
        }
        
        return copy;
    }
}
