package NN;

import ActivationFunctions.ActivationFunction;

import java.io.Serializable;

public class Perceptron implements Serializable {

    /**
     * This class represents a perceptron in the NN
     */
    private ActivationFunction activationFuntion;
    private int id; // id should always start at 0
    private double bias = 0;
    private double output = 0;
    private double z = 0;

    //calculates the z and output value using the input, it´s used in forward propagation
    public void calculateInput(Double[][] input,Perceptron[] prev){
        double sum = 0;
        for(int i = 0 ; i<input[id].length;i++){
            sum += input[id][i]*prev[i].getOutput();
        }
        z = sum+bias;
        output= activationFuntion.calculateActivation(z);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }


    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
    public ActivationFunction getActivationFunction(){
        return activationFuntion;
    }
    public void setActivationFunction(ActivationFunction activationFuntion){
        this.activationFuntion = activationFuntion;
    }
    public double getZ(){
        return z;
    }
}
