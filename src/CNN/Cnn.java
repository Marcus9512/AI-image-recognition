package CNN;

import ActivationFunctions.ActivationFunction;
import ActivationFunctions.ReLu;
import ImageTools.ReadImage;
import Tools.MyMath;

import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Cnn {
    final ActivationFunction relu = new ReLu();

    int numberOfChannels = 3;
    int numberOfOutputs = 0;
    int numberOfHiddenLayers = 3;

    int maxEpochs = 1000;

    int picW = 900;
    int picH = 900;

    Layer[] layers;
    ArrayList<Double[][]> weights = new ArrayList<>();
    ReadImage trainMaterial1;

    ArrayList<Double[][]> gradientWeights = new ArrayList<>();
    ArrayList<double[]> gradientBias = new ArrayList<>();
    ArrayList<double[]> gradientCost = new ArrayList<>();

    public Cnn(){

        init();
        train();
        saveNetwork("Neural-Net");

    }
    private void train(){
        BufferedImage bi ;
        int epochs = 0;
        while ((bi = trainMaterial1.getNextImage()) != null || epochs < maxEpochs){
            decodeImageToInputLayer(bi);
            forwardProp();
            backwardProp(0);
        }
    }

    private void decodeImageToInputLayer(BufferedImage im){
        Perceptron[] perceptrons = layers[0].getPerceptrons();
        for(int y =0 ; y<picW;y++){
            for(int x = 0; x<picH;x++){
                Color color = new Color(im.getRGB(x,y));
                //TODO implementera 3 lager rgb
                int colorSum = (color.getRed() +color.getBlue() +color.getGreen())/3;
                perceptrons[y*picW+x].setOutput(colorSum);
            }
        }
    }
    private void forwardProp(){
        for(int i = 1; i<layers.length;i++){
            Perceptron[] prev = layers[i-1].getPerceptrons();
            for(Perceptron perceptron: layers[i].getPerceptrons()){
                perceptron.calculateInput(weights.get(i-1),prev);
            }
        }
    }
    private void backwardProp(double solutionIndex){
        //för varje nod utom input:
        //cost = (faktiskt värde - önskat värde)^2

        Perceptron[] lastLayer = layers[layers.length-1].getPerceptrons();
        double[] cost = gradientCost.get(layers.length-1);

        for(int i = 0; i<cost.length;i++){
            if(i == solutionIndex){
                cost[i] = (lastLayer[i].getOutput() - 1) * (lastLayer[i].getOutput() - 1);
            }else{
                cost[i] = (lastLayer[i].getOutput()) * (lastLayer[i].getOutput());
            }
        }

        for(int i = layers.length-2; i>=0 ; i--){
            Perceptron[] perceptronsInCL = layers[i].getPerceptrons();
            Perceptron[] perceptronsInPL = layers[i+1].getPerceptrons();
            Double[][] currentWeights = weights.get(i);

            for(int j = 0; j< perceptronsInCL.length; i++){
                double z = 0;
                gradientCost.get(i)[j] = 0;
                for(int w = 0 ; w < currentWeights.length; w++){

                    z+= currentWeights[w][j] *  perceptronsInPL[w].getOutput();
                    z+= perceptronsInCL[j].getBias();

                    double correctionWeight = 0;

                    correctionWeight = MyMath.Dcdw(perceptronsInCL[j].getOutput(),gradientCost.get(i+1)[w] , z, perceptronsInCL[j].getActivationFunction(), perceptronsInPL[w]);
                    weights.get(i)[w][j] += correctionWeight;

                    gradientCost.get(i)[j] += MyMath.Dcda(perceptronsInCL[j].getOutput(),gradientCost.get(i+1)[w] , z, perceptronsInCL[j].getActivationFunction(), currentWeights[w][j]);

                }


            }
        }


    }
    private void init(){

        trainMaterial1 = new ReadImage("bilder");

        int hiddenSize = (picH*picW+numberOfOutputs)/2 ;
        layers = new Layer[numberOfHiddenLayers+2];

        layers[0] = new Layer(0,picH*picW, relu);
        layers[1] = new Layer(1,hiddenSize, relu);
        layers[2] = new Layer(2,hiddenSize, relu);
        layers[3] = new Layer(3,hiddenSize, relu);
        layers[4] = new Layer(4,numberOfOutputs, relu);

        gradientBias.add(new double[picH*picW]);
        gradientBias.add(new double[hiddenSize]);
        gradientBias.add(new double[hiddenSize]);
        gradientBias.add(new double[hiddenSize]);
        gradientBias.add(new double[numberOfOutputs]);

        gradientCost.add(new double[picH*picW]);
        gradientCost.add(new double[hiddenSize]);
        gradientCost.add(new double[hiddenSize]);
        gradientCost.add(new double[hiddenSize]);
        gradientCost.add(new double[numberOfOutputs]);

        for(int i = 0 ; i< layers.length-1;i++){
            //create weight matrix between each layer
            createWeightMatrix(layers[i].getPerceptrons().length,layers[i+1].getPerceptrons().length,weights,false);
            createWeightMatrix(layers[i].getPerceptrons().length,layers[i+1].getPerceptrons().length,gradientWeights,true);
        }


    }
    private void createWeightMatrix(int x,int y,ArrayList<Double[][]> addto ,boolean onlyZeros){
        Double[][] tmp = new Double[x][y];
        for(int i = 0; i< x ; i++){
            for(int j = 0; j < y ; j++){
                if(!onlyZeros)
                    tmp[i][j] = new Double(MyMath.rand(-10,10));
                else
                    tmp[i][j] = 0.0;
            }
        }
        addto.add(tmp);
    }

    private void saveNetwork(String name){
        Tools.SaveAndLoadNetwork.save(layers,weights,name);
    }
    public static void main(String[] args) {
        new Cnn();
    }
}
