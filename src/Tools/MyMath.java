package Tools;

import CNN.Layer;
import CNN.Perceptron;
import ActivationFunctions.ActivationFunction;

import java.util.Random;

public class MyMath {
    static Random random = new Random();
    public static double rand(){
        return random.nextDouble();
    }
    public static double rand2(int min,int max){
        return (Math.random() * ((max - min) + 1)) + min;
    }
    public static double grand(){
        return random.nextGaussian()*10;
    }
    public static double Dcdw(double a,double y, double z, ActivationFunction activationFunction, Perceptron prevLayer){
        return dcda(a,y) * dadz(z,activationFunction) * dzdw(prevLayer);
    }
    public static double Dcda(double a,double y, double z, ActivationFunction activationFunction, Double weight){
        return dcda(a,y) * dadz(z,activationFunction) * dzda(weight);
    }
    public static double Dcdb(double a,double y, double z, ActivationFunction activationFunction){
        return dcda(a,y) * dadz(z,activationFunction);
    }
    private static double dcda(double a, double y){
        return 2*(a-y);
    }
    private static double dadz(double z,ActivationFunction activationFunction){
        return activationFunction.getDerivative(z);
    }

    private static double dzdw(Perceptron prevNode ){
        return  prevNode.getOutput();
    }
    private static double dzda(Double weight){
        return weight;
    }


}
