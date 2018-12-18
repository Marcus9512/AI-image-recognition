package CNN;

import ActivationFunctions.ActivationFunction;

import java.io.Serializable;
import java.util.ArrayList;

public class Perceptron implements Serializable {
    private ActivationFunction activationFuntion;
    private int id; // id should always start at 0
    private double bias = 0.01;
    private double threshold;
    private double output = 0;


    public void calculateInput(Double[][] input,Perceptron[] prev){
        double sum = 0;
        for(int i = 0 ; i<input[0].length;i++){
            sum += input[id][i].doubleValue()*prev[i].getOutput();
        }
        double res = activationFuntion.calculateActivation(sum+bias);
        output = res > threshold ? res : 0;
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

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
}
