/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nn;

import java.util.concurrent.ThreadLocalRandom;
import nn.math.Random;

/**
 *
 * @author lazyf
 */
public class Neuron <T> {
    // self referencing 
    double inputs[];
    double weights[]; // all weights connected to inputs
    T data; //for input layer
    double weight;
    double weightedSum;
    Neuron outputs[];
    double e = 2.718281828459045; //euler's number
    double bias = Random.randomInt(5, -5
    );
    
    public Neuron(){
        //empty neuron defualt constructor
    }
    /**
     * Main Neuron of hidden layer if weight exists 
     * @param weight weight of this neuron
     * @param data input going into neuron
     */
    public Neuron(double weight, T data){
         this.weight = weight;
         this.data = data; //input going into neuron
    }
    
    /**
     * Main neuron if weights don't exist
     */
    public Neuron(double inputs[]){
         this.inputs = inputs;
         generateWeights();
    }
    
    public double weightedSum(int yPos) {
        //input * weight
        //multiply all inputs with neurons weights
        double sum = 0;
        sum = bias;
        //this is where bias will be added
        for (int i = 0; i < inputs.length; i++) {
            //System.out.println(inputs[i] * weights[i]);
            sum+=((inputs[i]*weights[i]));
        }
       // System.out.println("Bias: " + bias);
        //System.out.println("Sum: " + sum);
        return sigmoid(sum);
    }

    public void setOutputs(Neuron[] outputs) {
        this.outputs = outputs;
    }
     
     
    
    
    public void generateWeights(){
        weights = new double[inputs.length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Random.randomDouble(0, 1);
        }
    }


    public void setInputs(double inputs[]){
        this.inputs = inputs;
        generateWeights();
    }
    
    /**
     * Activation function introduces non-linearity 
     * @param number the number to squash
     * @return number between 0 and 1 
     */
    public double sigmoid(double number){
        return 1/(1 + Math.pow(e, -number));
    }
    
    public void mutateWeight(int i){
        weights[i] += Random.randomDouble(-1, 1);
    }

    public void setWeight(int i, double value){
        weights[i] = value;
    }
    public double getWeight(int i){
        return weights[i];
    }
    public double[] getWeights(){
        return this.weights;
    }
    
    public double getBias(){
        return bias;
    }
//    public double getFitness(){
//        return fitness;
//    }
    public void mutateBias(){
        double temp = bias;
        double add = Random.randomDouble(-1, 1);
        temp += add;
        bias += add; 
        //System.out.println(bias);
    }
    public void setBias(double bias){
        this.bias = bias;
    }
    
    @Override
    public Neuron clone(){
        Neuron temp = new Neuron();
        temp.bias = this.bias;
        temp.weights = this.weights;
        return temp;
    }
}
