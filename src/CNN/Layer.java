package CNN;

import Tools.MyMath;

public class Layer {
    private Perceptron[] perceptrons;
    private int id;

    public Layer(int id,int numberOfPerceptrons){
        this.id = id;
        perceptrons = new Perceptron[numberOfPerceptrons];
        for(int i = 0; i< numberOfPerceptrons; i++){
            perceptrons[i] = new Perceptron();
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
