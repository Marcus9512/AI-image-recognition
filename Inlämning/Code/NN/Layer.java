package NN;

import ActivationFunctions.ActivationFunction;
import Tools.MyMath;

import java.io.Serializable;

public class Layer implements Serializable {

    /**
     *  This class provides data structures for the layers in the neural network
     */

    private Perceptron[] perceptrons;
    private int id;

    // initializes the layer with a id, requested amount of neurons and an activation function.
    public Layer(int id, int numberOfPerceptrons, ActivationFunction activationFunction){
        this.id = id;
        perceptrons = new Perceptron[numberOfPerceptrons];
        for(int i = 0; i< numberOfPerceptrons; i++){
            perceptrons[i] = new Perceptron();
            perceptrons[i].setId(i);
            perceptrons[i].setActivationFunction(activationFunction);
        }
    }
    // randomize the perceptron's bias values
    public void randomizePerceptron(){
        for(int i = 0; i< perceptrons.length; i++){
            double rand = MyMath.rand();
           // perceptrons[i].setThreshold(-rand);
            perceptrons[i].setBias(rand);
        }
    }
    public Perceptron[] getPerceptrons(){
        return perceptrons;
    }
    public int getId(){
        return id;
    }
}
