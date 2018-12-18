package CNN;

import Tools.MyMath;

import java.util.ArrayList;

public class Cnn {

    int numberOfChannels = 3;
    int numberOfOutputs = 0;
    int numberOfHiddenLayers = 3;

    int picW = 900;
    int picH = 900;

    Layer[] layers;
    ArrayList<Double[][]> weights = new ArrayList<>();

    public Cnn(){
        // läs in en bild
        // träna
        // reptera till bilder är slut

        // spara
    }
    private void init(){
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

    public static void main(String[] args) {
        new Cnn();
    }
}
