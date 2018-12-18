package CNN;

import ImageTools.ReadImage;
import Tools.MyMath;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Cnn {

    int numberOfChannels = 3;
    int numberOfOutputs = 0;
    int numberOfHiddenLayers = 3;

    int numberOfItereations = 1000;

    int picW = 900;
    int picH = 900;

    Layer[] layers;
    ArrayList<Double[][]> weights = new ArrayList<>();
    ReadImage trainMaterial1;

    public Cnn(){

        init();
        train();
        saveNetwork("Neural-Net");

    }
    private void train(){
        BufferedImage bi ;
        int count = 0;
        while ((bi = trainMaterial1.getNextImage()) != null || count < numberOfItereations ){
            decodeImageToInputLayer(bi);
            forwardProp();
            backwardProp();
        }
    }

    private void decodeImageToInputLayer(BufferedImage im){
        Perceptron[] perceptrons = layers[0].getPerceptrons();
        for(int y =0 ; y<picW;y++){
            for(int x = 0; x<picH;x++){
                Color color = new Color(im.getRGB(x,y));
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
    private void backwardProp(){

    }
    private void init(){

        trainMaterial1 = new ReadImage("bilder");

        int hiddenSize = (picH*picW+numberOfOutputs)/2 ;
        layers = new Layer[numberOfHiddenLayers+2];

        layers[0] = new Layer(0,picH*picW);
        layers[1] = new Layer(1,hiddenSize);
        layers[2] = new Layer(2,hiddenSize);
        layers[3] = new Layer(3,hiddenSize);
        layers[4] = new Layer(4,numberOfOutputs);

        for(int i = 0 ; i< layers.length-1;i++){
            createWeightMatrix(layers[i].getPerceptrons().length,layers[i+1].getPerceptrons().length);
        }
    }
    private void createWeightMatrix(int x,int y){
        Double[][] tmp = new Double[x][y];
        for(int i = 0; i< x ; i++){
            for(int j = 0; j < y ; j++){
                tmp[i][j] = new Double(MyMath.rand(-10,10));
            }
        }
        weights.add(tmp);
    }
    private void saveNetwork(String name){
        Tools.SaveAndLoadNetwork.save(layers,weights,name);
    }
    public static void main(String[] args) {
        new Cnn();
    }
}
