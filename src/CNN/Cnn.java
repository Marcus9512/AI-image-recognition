package CNN;

import ActivationFunctions.ActivationFunction;
import ActivationFunctions.ReLu;
import ActivationFunctions.Sigmoid;
import ImageTools.Dataset;
import ImageTools.ReadImage;
import Tools.MyMath;

import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Cnn {
    final ActivationFunction relu = new ReLu();
    final ActivationFunction sigmoid = new Sigmoid();
    final double learning_rate = 3.0;

    int numberOfChannels = 3;
    int numberOfOutputs = 10;
    int numberOfHiddenLayers = 1;

    int maxEpochs = 200;

    int picW = 28;
    int picH =  28;

    Layer[] layers;
    ArrayList<Double[][]> weights = new ArrayList<>();
    ReadImage trainMaterial;
    ReadImage testMaterial;


    boolean[] done = new boolean[10];

    ArrayList<Double[][]> gradientWeights = new ArrayList<>();
    ArrayList<double[]> gradientBias = new ArrayList<>();
    ArrayList<double[]> gradientError = new ArrayList<>();

    String serachPath = "Numbers/";
    String testPath = "testing/";

    public Cnn(boolean newNetwork, String path){

        if(newNetwork) {
            init();
        }else{
            loadNetwork(path);
        }

    }

    public void trainNetwork(){
        train();
        saveNetwork("Neural-Net");
    }
    public int runNetwork(BufferedImage bi){
        decodeImageToInputLayer(bi);
        forwardProp();

        int pick = 0;
        double brightest = Double.MIN_VALUE;
        Perceptron[] perceptrons = layers[layers.length-1].getPerceptrons();
        for(int i = 0 ; i< perceptrons.length; i++ ){
            if(perceptrons[i].getOutput() > brightest){
                pick = i;
                brightest = perceptrons[i].getOutput();
            }
        }
        return pick;
    }
    private void train(){
        Dataset ds ;

        double pro = 0;
        int minibatch_size = 0;

        for(int epochs = 0; epochs < maxEpochs; epochs++ ) {

            double time = System.nanoTime();

            while ((ds = trainMaterial.getNext()) != null) {
                decodeImageToInputLayer(ds.getImage());

                backwardProp(ds.getSolution());

                minibatch_size++;
                if(minibatch_size == 10) {
                    applyTrainging(minibatch_size);
                    minibatch_size = 0;
                }
            }
            if(minibatch_size != 0){
                applyTrainging(minibatch_size);
                minibatch_size = 0;
            }
            double stop = System.nanoTime();

            readSolution(epochs,time,stop);

            if(pro > 90)
                break;

            for(int i = 0 ;i<done.length;i++){
                done[i] = false;
            }
            trainMaterial.reset();

        }
    }
    private void readSolution(int epoch , double start, double stop){
        Dataset ds;
        int c = 0;
        int w = 0;
        while ((ds = testMaterial.getNext()) != null){
            decodeImageToInputLayer(ds.getImage());
            forwardProp();

            int pick = 0;
            double brightest = Double.MIN_VALUE;
            Perceptron[] perceptrons = layers[layers.length-1].getPerceptrons();
            for(int i = 0 ; i< perceptrons.length; i++ ){
                if(perceptrons[i].getOutput() > brightest){
                    pick = i;
                    brightest = perceptrons[i].getOutput();
                }
            }
            if(pick == ds.getSolution()){
                c++;
            }else {
                w++;
            }

        }
        testMaterial.reset();
        System.out.println("After epoch "+ epoch+", correct answers: "+c+" of "+(w+c)+" it took "+(stop-start)/1000000000.0 +"s");



    }/*
    private Dataset getNext(){
        if(trainMaterial1.hasNext() && trainMaterial2.hasNext()){
            int tmp = MyMath.rand(-5,5);
            if(tmp >0)
                return new Dataset(trainMaterial1.getNextImage(),0);
            else
                return new Dataset(trainMaterial2.getNextImage(),1);
        }else if(trainMaterial1.hasNext()){
            return new Dataset(trainMaterial1.getNextImage(),0);
        }else if(trainMaterial2.hasNext()){
            return new Dataset(trainMaterial2.getNextImage(),1);
        }
       if(trainMaterial1.hasNext()){
           if(!done[0]){
               System.out.println("Dataset 0");
               done[0] = true;
           }
           return new Dataset(trainMaterial1.getNextImage(),0);
       }else if(trainMaterial2.hasNext()){
           if(!done[1]){
               System.out.println("Dataset 1");
               done[1] = true;
           }
           return new Dataset(trainMaterial2.getNextImage(),1);
       }else if(trainMaterial3.hasNext()){
           if(!done[2]){
               System.out.println("Dataset 2");
               done[2] = true;
           }
           return new Dataset(trainMaterial3.getNextImage(),2);
       }else if(trainMaterial4.hasNext()){
           if(!done[3]){
               System.out.println("Dataset 3");
               done[3] = true;
           }
           return new Dataset(trainMaterial4.getNextImage(),3);
       }else if(trainMaterial5.hasNext()){
           if(!done[4]){
               System.out.println("Dataset 4");
               done[4] = true;
           }
           return new Dataset(trainMaterial5.getNextImage(),4);
       }else if(trainMaterial6.hasNext()){
           if(!done[5]){
               System.out.println("Dataset 5");
               done[5] = true;
           }
           return new Dataset(trainMaterial6.getNextImage(),5);
       }else if(trainMaterial7.hasNext()){
           if(!done[6]){
               System.out.println("Dataset 6");
               done[6] = true;
           }
           return new Dataset(trainMaterial7.getNextImage(),6);
       }else if(trainMaterial8.hasNext()){
           if(!done[7]){
               System.out.println("Dataset 7");
               done[7] = true;
           }
           return new Dataset(trainMaterial8.getNextImage(),7);
       }else if(trainMaterial9.hasNext()){
           if(!done[8]){
               System.out.println("Dataset 8");
               done[8] = true;
           }
           return new Dataset(trainMaterial9.getNextImage(),8);
       }else if(trainMaterial10.hasNext()){
           if(!done[9]){
               System.out.println("Dataset 9");
               done[9] = true;
           }
           return new Dataset(trainMaterial10.getNextImage(),9);
       }
        return null;
    }
*/
    private void decodeImageToInputLayer(BufferedImage im){
        Perceptron[] perceptrons = layers[0].getPerceptrons();
        for(int y =0 ; y<picW;y++){
            for(int x = 0; x<picH;x++){
                Color color = new Color(im.getRGB(x,y));
                //TODO implementera 3 lager rgb
                double colorSum = ((double)( color.getRed()+color.getBlue()+color.getGreen() ))/(255.0*3.0);
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
      // printMatrix();
      //  printWheights(weights);
     /*               try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
    }
    private void backwardProp(int solutionIndex){
        //för varje nod utom input:
        //cost = (faktiskt värde - önskat värde)^2

        forwardProp();
        calculateOutputLayer(solutionIndex);
        calculateRemaingingHiddenLayers();
        resetError();



    }
    private void calculateOutputLayer(int solution){
        Perceptron[] lminusOne = layers[layers.length-2].getPerceptrons();
        Perceptron[] l = layers[layers.length-1].getPerceptrons();


        //calculate solution
        double[] y = new double[l.length];
        for(int i = 0 ; i<y.length;i++){
            if(i == solution)
                y[i] = 1.0;
            else
                y[i] = 0.0;
        }

        for(int j = 0 ; j< l.length;j++){
         /*   double z = 0;
            // calculate z
            for(int k = 0 ; k< lminusOne.length;k++){
                z += weights.get(layers.length-2)[j][k] * lminusOne[k].getOutput();
            }
            z +=  + l[j].getBias();*/

            //calculate gradient w and bias
            double error =  (l[j].getOutput() - y[j]) * l[j].getActivationFunction().getDerivative(l[j].getZ());
            for (int k = 0; k < lminusOne.length; k++) {
                gradientWeights.get(layers.length - 2)[j][k] = lminusOne[k].getOutput() * error;
            }
            gradientBias.get(gradientBias.size() - 1)[j] = error;
            gradientError.get(gradientError.size()-1)[j] = error;

        }

    }

    private void calculateRemaingingHiddenLayers(){
        for(int l = layers.length-2; l>0;l--) {
            Perceptron[] cl = layers[l].getPerceptrons();
            Perceptron[] lplusOne = layers[l+1].getPerceptrons();
            Perceptron[] lminusOne = layers[l-1].getPerceptrons();

            for (int j = 0; j < cl.length; j++) {
           /*     double z = 0;
                // calculate z
                for (int k = 0; k < lminusOne.length; k++) {
                   // System.out.println( lminusOne[k].getOutput());
                    z += weights.get(l-1)[j][k] * lminusOne[k].getOutput();
                }
                z+= cl[j].getBias();*/

                for (int k = 0; k < lplusOne.length; k++) {
                    gradientError.get(l)[j]+= weights.get(l)[k][j] * gradientError.get(l+1)[k] *cl[j].getActivationFunction().getDerivative(cl[j].getZ());
                 //   System.out.println(weights.get(l)[k][j]+" "+gradientError.get(l+1)[k]+" "+cl[j].getActivationFunction().getDerivative(z));
                }


                //calculate gradient w and bias
                for (int k = 0; k < lminusOne.length; k++) {
                    gradientWeights.get(l-1)[j][k] += lminusOne[k].getOutput() * gradientError.get(l)[j];
                }
                gradientBias.get(l)[j] += gradientError.get(l)[j];

            }
        }
    }

    private void applyTrainging(double minibatch_size){
       // printWheights(gradientWeights);
      //  printBias();
        for(int start = 0 ; start < weights.size(); start++){
            Double[][] cor = gradientWeights.get(start);
            Double[][] curr = weights.get(start);
            for(int i = 0; i< curr.length;i++){
                for(int j = 0; j <curr[0].length;j++){
                 //   System.out.println(cor[i][j]);
                      curr[i][j]-= (learning_rate/minibatch_size)*cor[i][j];
                      cor[i][j] = 0.0;
                }
            }
        }
        for(int i = 0; i< layers.length;i++){
            Perceptron[] perceptron = layers[i].getPerceptrons();
            for(int j = 0; j< perceptron.length;j++){
                perceptron[j].setBias(perceptron[j].getBias()- (learning_rate/minibatch_size)*gradientBias.get(i)[j]);
                gradientBias.get(i)[j] = 0.0;
            }
        }
    }
    private void resetError(){
        for(double[] doubles : gradientError){
            for(int i = 0 ; i< doubles.length;i++){
                doubles[i] = 0;
            }
        }
    }

    private void init(){

        trainMaterial = new ReadImage(serachPath);
        testMaterial = new ReadImage(testPath);


        int hiddenSize = 30 ;
        layers = new Layer[numberOfHiddenLayers+2];

        System.out.println("number of outputs: "+ numberOfOutputs);
        System.out.println("number of hidden layers: "+ numberOfHiddenLayers);
        System.out.println("hiddensize: "+ hiddenSize);


        layers[0] = new Layer(0,picH*picW, sigmoid);
        layers[1] = new Layer(1,hiddenSize, sigmoid);
        //layers[2] = new Layer(2,hiddenSize, sigmoid);
       // layers[3] = new Layer(3,hiddenSize, sigmoid);
        layers[2] = new Layer(4,numberOfOutputs, sigmoid);

        for(Layer layer : layers){
            layer.randomizePerceptron();
        }

        gradientBias.add(0,new double[picH*picW]);
        gradientBias.add(1,new double[hiddenSize]);
     //   gradientBias.add(2,new double[hiddenSize]);
      //  gradientBias.add(3,new double[hiddenSize]);
        gradientBias.add(2,new double[numberOfOutputs]);

        gradientError.add(0,new double[picH*picW]);
        gradientError.add(1,new double[hiddenSize]);
     //   gradientError.add(2,new double[hiddenSize]);
     //   gradientError.add(3,new double[hiddenSize]);
        gradientError.add(2,new double[numberOfOutputs]);



        for(int i = 0 ; i< layers.length-1;i++){
            //create weight matrix between each layer
            createWeightMatrix(layers[i+1].getPerceptrons().length,layers[i].getPerceptrons().length,weights,false);
            createWeightMatrix(layers[i+1].getPerceptrons().length,layers[i].getPerceptrons().length,gradientWeights,true);
        }
    }
    private void createWeightMatrix(int x,int y,ArrayList<Double[][]> addto ,boolean onlyZeros){
        Double[][] tmp = new Double[x][y];
        for(int i = 0; i< x ; i++){
            for(int j = 0; j < y ; j++){
                if(!onlyZeros)
                    tmp[i][j] = MyMath.rand2(-10,10);
                else
                    tmp[i][j] = 0.0;
            }
        }
        addto.add(tmp);
    }

    private void printMatrix(){
        for(Layer layer : layers){
            for(Perceptron perceptron : layer.getPerceptrons()){
                System.out.print(perceptron.getBias()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    private void printBias(){
        for(double[] doubles : gradientBias){
            for(double d : doubles){
                System.out.print(d+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    private void printWheights(ArrayList<Double[][]> in){
        int i = 0;
        for(Double[][] doubles : in){
            System.out.println(i);
            for(int x = 0 ; x<doubles.length;x++){
                for(int y = 0; y<doubles[0].length; y++){
                    System.out.print(doubles[x][y]+" ");
                }
                System.out.println();
            }

            i++;
        }
        System.out.println();
    }
    private void saveNetwork(String name){
        Tools.SaveAndLoadNetwork.save(layers,weights,name);
    }
    private void loadNetwork(String networkName){
        Tools.SaveAndLoadNetwork.NetworkHolder networkHolder = Tools.SaveAndLoadNetwork.load(networkName);
        System.out.println(networkHolder);
        layers = networkHolder.getLayer();
        weights = networkHolder.getWeights();
    }
    public static void main(String[] args) {
        new Cnn(true,"").trainNetwork();
    }
}
