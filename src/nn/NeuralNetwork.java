
package nn;

import game.logic.Player;
import nn.math.Random;

/**
 *
 * @author lazyf
 */
public class NeuralNetwork extends Player{
    double inputs[];
    Neuron hidden[];
    Neuron output;
    int inputNum;
    long fitness = 0; // fitness for genetic learning
    public boolean greaterPerformer = false;
    public NeuralNetwork(){
        //empty constructor
        if(inputs != null) createStructure();
    }
    public NeuralNetwork(NeuralNetwork nn){
        this(nn.inputNum, nn.hidden, nn.output);
    }
    
    public NeuralNetwork(int inputNum, Neuron[] hidden){
        this.inputNum = inputNum;
        this.hidden = hidden;
        createStructure();
    }
     public NeuralNetwork(int inputNum, Neuron[] hidden, Neuron output){
        this.inputNum = inputNum;
        this.hidden = hidden;
        this.output = output;
    }

    public NeuralNetwork(int inputNum, int hiddenNum){
        this.inputNum = inputNum;
        this.hidden = new Neuron[hiddenNum];
        inputs = new double[inputNum];
        createStructure(inputs);
        //creates array with number of inputs
        //hard code for example
        //3 inputs
        //4 hidden
    }
    /**
     * Creates structure of neurons. creates hidden 
     * neurons, connects inputs to them, connects then to the output neuron
     * and then the output neuron back to all hidden neurons
     * @param inputs the initial inputs
     */
    private void createStructure(double [] inputs){
        //might cause problem. Hidden/2 if so
        Neuron layer2[] = new Neuron[hidden.length];
        //creates the second layer in the hidden layer
        for (int i = 0; i < hidden.length; i++) {
            layer2[i] = new Neuron();
        }
       
        output = new Neuron();
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = new Neuron(inputs);
            hidden[i].setOutputs(layer2);        }
           
    }
    
    
    /**
     * mutate neuron at index i
     * @param i neuron in array to mutate
     */
    public void mutate(int i){
        //mutate ll weights of neuron by -1, 1
        for (int j = 0; j < hidden[i].getWeights().length; j++) {
            hidden[i].mutateWeight(j);
        }
       
        //mutate bias by -1, 1
        for (int j = 0; j < hidden.length; j++) {
            hidden[j].mutateBias();
            output.mutateWeight(j);
        }
        output.mutateBias();
    }
   /**
    * 
    */
   public void createStructure(){
       output = new Neuron();
   }
    
    
    public double feedForward(double [] inputs){
        this.inputs = inputs;
        double outputIns[] = new double[hidden.length];
        for (int i = 0; i < hidden.length; i++) {
            hidden[i].inputs = inputs;
            hidden[i].weightedSum = 
            hidden[i].weightedSum(this.getY());
            ///////////Output/////////
            outputIns[i] = hidden[i].weightedSum;
        }
        output.setInputs(outputIns);
        
        output.weightedSum = output.weightedSum(this.getY());
        //output .inputs = weighted sum of hidden neurons
        return output.weightedSum;
    }
    
    /**
     * Sets the fitness of the network. Used for Genetic learning algorithim 
     * @param fitness 
     */
    public void setFitness(long fitness){
        this.fitness = fitness;
    }
    public void changeFitness(int change){
        fitness+=change;
    }
    public double getFitness(){
        return fitness;
    }
    
    public Neuron[] getHidden(){
        return hidden;
    }
    public Neuron[] cloneHidden(){
        Neuron[] temp = new Neuron[hidden.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = hidden[i];
        }
        return temp;
    }
    
    public Neuron getOutput(){
        return output;
    }
    public void setOutput(Neuron output){
        this.output = output;
    }
    
    public Neuron getHidden(int i){
        return hidden[i];
    }
    
    public void setHidden(int i, Neuron temp){
        hidden[i] = temp;
    }
     public void setHidden(Neuron[] temps){
        hidden = temps;
    }
    
     public double[] getInputs(){
        return inputs;
    }
     
    public void setBias(int i, double bias){
        hidden[i].setBias(bias);
    } 
    public double getBias(int i){
        return hidden[i].getBias();
    }
    
    private void output(int array[]){
//        System.out.print("[");
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i]);
//        }
//        System.out.print("]");
//        System.out.println("");
    }
    private void output(double array[]){
//        System.out.print("[");
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i]);
//        }
//        System.out.print("]");
//        System.out.println("");
    }
    
    
    @Override
    public NeuralNetwork clone() {
        Neuron tempHiddens[] = new Neuron[hidden.length];
        for (int i = 0; i < tempHiddens.length; i++) {
            tempHiddens[i] = hidden[i].clone();
        }
        Neuron output = this.output.clone();
        NeuralNetwork nn = new NeuralNetwork(this.inputNum, tempHiddens, output);
        nn.fitness = this.fitness;
        return nn;
    }
    
    
}
