package CNN;

import ActivationFunctions.ActivationFunction;
import Tools.MyMath;

import java.io.Serializable;

public class Layer implements Serializable {
    private Perceptron[] perceptrons;
    private int id;

    public Layer(int id, int numberOfPerceptrons, ActivationFunction activationFunction){
        this.id = id;
        perceptrons = new Perceptron[numberOfPerceptrons];
        for(int i = 0; i< numberOfPerceptrons; i++){
            perceptrons[i] = new Perceptron();
            perceptrons[i].setId(i);
            perceptrons[i].setActivationFunction(activationFunction);
        }
    }
    public void randomizePerceptron(){
        for(int i = 0; i< perceptrons.length; i++){
            perceptrons[i].setThreshold(MyMath.rand(-10,10));
        }
    }
    public Perceptron[] getPerceptrons(){
        return perceptrons;
    }
    public int getId(){
        return id;
    }
}
