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

    int numberOfChannels = 3;
    int numberOfOutputs = 10;
    int numberOfHiddenLayers = 3;

    int maxEpochs = 1000000;

    int picW = 28;
    int picH =  28;

    Layer[] layers;
    ArrayList<Double[][]> weights = new ArrayList<>();
    ReadImage trainMaterial1;
    ReadImage trainMaterial2;
    ReadImage trainMaterial3;
    ReadImage trainMaterial4;
    ReadImage trainMaterial5;
    ReadImage trainMaterial6;
    ReadImage trainMaterial7;
    ReadImage trainMaterial8;
    ReadImage trainMaterial9;
    ReadImage trainMaterial10;

    boolean[] done = new boolean[10];

    ArrayList<Double[][]> gradientWeights = new ArrayList<>();
    ArrayList<double[]> gradientBias = new ArrayList<>();
    ArrayList<double[]> gradientError = new ArrayList<>();

    String serachPath = "Numbers/";

    public Cnn(){

        init();
        train();
        saveNetwork("Neural-Net");

    }
    private void train(){
        Dataset ds ;
        double correct = 0;
        double notCorrect = 0;
        double pro = 0;
        for(int epochs = 0; epochs < maxEpochs; epochs++ ) {

            double time = System.nanoTime();

            while ((ds = getNext()) != null) {
                decodeImageToInputLayer(ds.getImage());
                forwardProp();
                if(readSolution(ds.getSolution())){
                    correct++;
                }else {
                    notCorrect++;
                }
                backwardProp(ds.getSolution());
            //    System.out.println("Iteration complete");
                applyTrainging();
              //  printMatrix();
            }
            double stop = System.nanoTime();
            pro = correct/(notCorrect+correct);

            System.out.println("After epoch "+ epochs+", %correct answears: "+pro+" it took "+(stop-time)/1000000000.0 +"s");

            notCorrect = 0;
            correct = 0;
            if(pro > 90)
                break;

            for(int i = 0 ;i<done.length;i++){
                done[i] = false;
            }
            trainMaterial1.reset();
            trainMaterial2.reset();
            trainMaterial3.reset();
            trainMaterial4.reset();
            trainMaterial5.reset();
            trainMaterial6.reset();
            trainMaterial7.reset();
            trainMaterial8.reset();
            trainMaterial9.reset();
            trainMaterial10.reset();
        }
    }
    private boolean readSolution(int solution){
        int pick = 0;
        double brightest = Double.MIN_VALUE;
        Perceptron[] perceptrons = layers[layers.length-1].getPerceptrons();
        for(int i = 0 ; i< perceptrons.length; i++ ){
            if(perceptrons[i].getOutput() > brightest){
                pick = i;
                brightest = perceptrons[i].getOutput();
            }
        }
        if(pick == solution)
            return true;
        return false;
    }
    private Dataset getNext(){
       /* if(trainMaterial1.hasNext() && trainMaterial2.hasNext()){
            int tmp = MyMath.rand(-5,5);
            if(tmp >0)
                return new Dataset(trainMaterial1.getNextImage(),0);
            else
                return new Dataset(trainMaterial2.getNextImage(),1);
        }else if(trainMaterial1.hasNext()){
            return new Dataset(trainMaterial1.getNextImage(),0);
        }else if(trainMaterial2.hasNext()){
            return new Dataset(trainMaterial2.getNextImage(),1);
        }*/
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
       //printMatrix();
      //  printWheights(weights);
    /*                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
    }
    private void backwardProp(int solutionIndex){
        //för varje nod utom input:
        //cost = (faktiskt värde - önskat värde)^2

        calculateOutputLayer(solutionIndex);
        calculateRemaingingHiddenLayers();

       /* Perceptron[] lastLayer = layers[layers.length-1].getPerceptrons();
        double[] cost = gradientCost.get(layers.length-1);

        for(int i = 0; i<cost.length;i++){
            if(i == solutionIndex){
                cost[i] = (lastLayer[i].getOutput() - 1) * (lastLayer[i].getOutput() - 1);
            }else{
                cost[i] = (lastLayer[i].getOutput()) * (lastLayer[i].getOutput());
            }
        }*/

        /*
        for(int i = layers.length-2; i>=0 ; i--){
            Perceptron[] perceptronsInCL = layers[i].getPerceptrons();
            Perceptron[] perceptronsInPL = layers[i+1].getPerceptrons();
            Double[][] currentWeights = weights.get(i);

            for(int j = 0; j< perceptronsInCL.length; j++){
                double z = 0;
                gradientCost.get(i)[j] = 0;
                for(int w = 0 ; w < currentWeights.length; w++){

                    z+= currentWeights[w][j] *  perceptronsInPL[w].getOutput();
                    z+= perceptronsInCL[j].getBias();

                    double correctionWeight = 0;
                /*    System.out.println(" current: i "+i+" current w "+w +" current j "+j);
                    System.out.println("range of w: "+(currentWeights.length-1)+" range of j: "+(perceptronsInCL.length-1)+" range of gradientCost(i+1): "+gradientCost.get(i+1).length+" range of perceptronsInCL "+perceptronsInCL.length+" range of perceptronsInPL "+perceptronsInPL.length);
                    correctionWeight = MyMath.Dcdw(perceptronsInCL[j].getOutput(),gradientCost.get(i+1)[w] , z, perceptronsInCL[j].getActivationFunction(), perceptronsInPL[w]);
                    gradientWeights.get(i)[w][j] += correctionWeight;

                    gradientCost.get(i)[j] += MyMath.Dcda(perceptronsInCL[j].getOutput(),gradientCost.get(i+1)[w] , z, perceptronsInCL[j].getActivationFunction(), currentWeights[w][j]);
                    gradientBias.get(i)[j] += MyMath.Dcdb(perceptronsInCL[j].getOutput(),gradientCost.get(i+1)[w] , z, perceptronsInCL[j].getActivationFunction());

                }
              //  System.out.println(":)");


            }
        }*/


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
            double z = 0;
            // calculate z
            for(int k = 0 ; k< lminusOne.length;k++){
                z += weights.get(layers.length-2)[j][k] * lminusOne[k].getOutput();
            }
            z +=  + l[j].getBias();

            //calculate gradient w and bias
            double error =  2*(l[j].getOutput() - y[j]) * l[j].getActivationFunction().getDerivative(z);
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
                double z = 0;
                // calculate z
                for (int k = 0; k < lminusOne.length; k++) {
                   // System.out.println( lminusOne[k].getOutput());
                    z += weights.get(l-1)[j][k] * lminusOne[k].getOutput();
                }
                z+= cl[j].getBias();

                for (int k = 0; k < lplusOne.length; k++) {
                    gradientError.get(l)[j]+= weights.get(l)[k][j] * gradientError.get(l+1)[k] *cl[j].getActivationFunction().getDerivative(z);
                 //   System.out.println(weights.get(l)[k][j]+" "+gradientError.get(l+1)[k]+" "+cl[j].getActivationFunction().getDerivative(z));
                }


                //calculate gradient w and bias
                for (int k = 0; k < lminusOne.length; k++) {
                    gradientWeights.get(l-1)[j][k] = lminusOne[k].getOutput() * gradientError.get(l)[j];
                }
                gradientBias.get(l)[j] = gradientError.get(l)[j];

            }
        }
    }

    private void applyTrainging(){
     //   printWheights(gradientWeights);
        for(int start = 0 ; start < weights.size(); start++){
            Double[][] cor = gradientWeights.get(start);
            Double[][] curr = weights.get(start);
            for(int i = 0; i< curr.length;i++){
                for(int j = 0; j <curr[0].length;j++){
                 //   System.out.println(cor[i][j]);
                      curr[i][j]-= cor[i][j];
                }
            }
        }
        for(int i = 0; i< layers.length;i++){
            Perceptron[] perceptron = layers[i].getPerceptrons();
            for(int j = 0; j< perceptron.length;j++){
                perceptron[j].setBias(perceptron[j].getBias()- gradientBias.get(i)[j]);
            }
        }
    }
    private void init(){

        trainMaterial1 = new ReadImage(serachPath+"0");
        trainMaterial2 = new ReadImage(serachPath+"1");
        trainMaterial3 = new ReadImage(serachPath+"2");
        trainMaterial4 = new ReadImage(serachPath+"3");
        trainMaterial5 = new ReadImage(serachPath+"4");
        trainMaterial6 = new ReadImage(serachPath+"5");
        trainMaterial7 = new ReadImage(serachPath+"6");
        trainMaterial8 = new ReadImage(serachPath+"7");
        trainMaterial9 = new ReadImage(serachPath+"8");
        trainMaterial10 = new ReadImage(serachPath+"9");

        int hiddenSize = 30 ;
        layers = new Layer[numberOfHiddenLayers+2];

        System.out.println("number of outputs: "+ numberOfOutputs);
        System.out.println("number of hidden layers: "+ numberOfHiddenLayers);
        System.out.println("hiddensize: "+ hiddenSize);


        layers[0] = new Layer(0,picH*picW, sigmoid);
        layers[1] = new Layer(1,hiddenSize, sigmoid);
        layers[2] = new Layer(2,hiddenSize, sigmoid);
        layers[3] = new Layer(3,hiddenSize, sigmoid);
        layers[4] = new Layer(4,numberOfOutputs, sigmoid);

        for(Layer layer : layers){
            layer.randomizePerceptron();
        }

        gradientBias.add(0,new double[picH*picW]);
        gradientBias.add(1,new double[hiddenSize]);
        gradientBias.add(2,new double[hiddenSize]);
        gradientBias.add(3,new double[hiddenSize]);
        gradientBias.add(4,new double[numberOfOutputs]);

        gradientError.add(0,new double[picH*picW]);
        gradientError.add(1,new double[hiddenSize]);
        gradientError.add(2,new double[hiddenSize]);
        gradientError.add(3,new double[hiddenSize]);
        gradientError.add(4,new double[numberOfOutputs]);



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
                System.out.print(perceptron.getOutput()+" ");
            }
            System.out.println();
        }
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
    }
    private void saveNetwork(String name){
        Tools.SaveAndLoadNetwork.save(layers,weights,name);
    }
    public static void main(String[] args) {
        new Cnn();
    }
}
