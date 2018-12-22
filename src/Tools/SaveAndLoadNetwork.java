package Tools;

import CNN.Layer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveAndLoadNetwork  {

    private static final String dir = "SavedNetworks/";

    public static void save(Layer[] layer,ArrayList<Double[][]> weights,String name){

        File file = new File("SavedNetworks");
        if(!file.exists()){
            file.mkdir();
        }
        int i = 0;
        while (true){
            if(i == 0) {
                file= new File(dir+name);
            }else {
                file = new File(dir+name+""+i);
            }
            if (file.exists()) {
                i++;
            }else{
                break;
            }
        }

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new NetworkHolder(layer,weights));

            objectOutputStream.flush();
            objectOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static NetworkHolder load(String name){
        File file = new File(dir+name);
        if(!file.exists())
            return null;

        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return (NetworkHolder) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class NetworkHolder implements Serializable{

        private Layer[] layer;
        private ArrayList<Double[][]> weights;

        public NetworkHolder(Layer[] layer, ArrayList<Double[][]> weights) {
            this.layer = layer;
            this.weights = weights;
        }

        public Layer[] getLayer() {
            return layer;
        }

        public void setLayer(Layer[] layer) {
            this.layer = layer;
        }

        public ArrayList<Double[][]> getWeights() {
            return weights;
        }

        public void setWeights(ArrayList<Double[][]> weights) {
            this.weights = weights;
        }
    }

}
