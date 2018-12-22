package CNN;

import ActivationFunctions.ActivationFunction;

import java.io.Serializable;
import java.util.ArrayList;

public class Perceptron implements Serializable {
    private ActivationFunction activationFuntion;
    private int id; // id should always start at 0
    private double bias = -0;
    private double threshold = 0;
    private double output = 0;
    private double z = 0;


    public void calculateInput(Double[][] input,Perceptron[] prev){
        double sum = 0;
        for(int i = 0 ; i<input[id].length;i++){
            sum += input[id][i]*prev[i].getOutput();
         //   System.out.print(input[id][i]+" "+prev[i].getOutput()+" currentSum :"+sum+" |");
        }
      //  System.out.println(sum);
        z = sum+bias;
        output= activationFuntion.calculateActivation(z);

       // output = res > threshold ? 1 : 0;
        //System.out.println(output);
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
