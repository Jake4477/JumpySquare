package nn.genetics;

import game.jumpysquare.AINewGame;
import nn.NeuralNetwork;
import nn.math.Random;
import static nn.math.SearchAndSort.bubbleSortNN;

/**
 *
 * @author lazyf
 */
public class Genetics {
    NeuralNetwork[] best;
    boolean firstRun = true;
    

    public Genetics() {

    }

    /**
     * Returns a population array of children with mixed traits from two parents
     *
     * @param ranked the sorted players
     * @return an array of mixed population
     */
    public NeuralNetwork[] crossing(NeuralNetwork[] ranked) {
        //Compare two parents and randomly cross genes. Example
        /**   Parent 1 [1] [3] [4] [1] [6] 
         *    Parent 2 [3] [8] [2] [6] [7] 
         *    Child =  [1] [8] [2] [1] [6]
         */
        //Keep same parent 1 and parent 2 in population
        NeuralNetwork p1Copy = ranked[0].clone();
        NeuralNetwork p2Copy = ranked[1].clone();
        //create new population
        NeuralNetwork[] children = ranked.clone();
        int preset = 0;
        //keep best performing by comparing past best and new best    
        if(firstRun){
            best = ranked.clone();
            firstRun = false;
        }
        for (int i = 0; i < best.length; i++) {
            if(best[i].getFitness() > ranked[i].getFitness()){
                children[i] = best[i].clone();
                ranked[i].greaterPerformer = false;
            }
            else{
                ////////////////////////////LOGIC/////////////////
            }
        }
   
        if(AINewGame.amount == 1){
            children[0] = ranked[ranked.length - 1];
            preset = 1;
        }
        else{
            for (int i = 1; i < 5; i++) {
                System.out.println(children[i].getFitness());
                children[i] = ranked[i].clone();
            }
            preset = 5;
        }
      
        if(children[0].getFitness() > 1000){
            return ranked.clone();
        }
        //look at each neurons weights
        for (int i = preset; i < ranked.length; i++) {
            //for every player
            if(Random.randomInt(3, 0) == 1){
               // p1Copy.getOutput().setBias(p2Copy.getOutput().getBias());
            }
            for (int j = 0; j < p1Copy.getHidden().length; j++) {
                //for every neuron 
                if(Random.randomInt(3, 0) == 1){
                    p1Copy.setBias(j, p2Copy.getBias(j));
                }
                for (int k = 0; k < p1Copy.getHidden(j).getWeights().length; k++) {
                    //for all weights in neuron
                   if(Random.randomInt(3, 0) == 1){
                       //swaps corresponding weights at random////////////
                      p1Copy.getHidden(j).setWeight(k, p2Copy.getHidden(j).getWeight(k));
                      if(Random.randomInt(100, 0) <= 2){
                          //chance to make weight completly random
                          p1Copy.getHidden(j).setWeight(k, Random.randomDouble(-1, 1));
                      }
                   }
                }
                //4% mutation rate
                if(Random.randomInt(100, 0) <= 4){
                    p1Copy.mutate(j);
                }
                
            }
            children[i] = p1Copy.clone();
        }
        return children;
    }

    /**
     * returns two most fit networks
     *
     * @param networks
     * @return array of two most fit Neural Networks
     */
    public NeuralNetwork[] fitness(NeuralNetwork[] networks) {
        NeuralNetwork[] ranked = bubbleSortNN(networks);
        return ranked;
    }


}
