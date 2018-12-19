package Tools;

import CNN.Layer;
import CNN.Perceptron;

import java.util.Random;

public class MyMath {
    static Random random = new Random();
    public static int rand(int low, int high){
        if(low>=high)
            return low;
        return random.nextInt(high-low)+low;
    }

    public static double dcdw(){
        return dcda() * dadz() * dzdw();
    }
    private static double dcda(double a, double y){
        return 2*(a-y);
    }
    private static double dadz(double a, Layer prevLayer){

    }

    private static double dzdw(Layer prevLayer){
        double sum = 0.0;
        for(Perceptron perceptron : prevLayer.getPerceptrons()){
            sum += perceptron.getOutput();
        }
        return  sum;
    }

}
