package NN;

import Tools.Holder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class MultipleNumbers {

    /**
     * This class is used for detecting several numbers within an image.
     */

    Holder[] images ;

    // Checks all possible 28x28 sub images that may contain a number.
    // Returns a list of holders that contains the cost value, the NN's estimated solution and x, y coordinates of the numbers.
    public ArrayList<Holder> analyzeImage(BufferedImage bufferedImage, Neural_Network nn){
        int loopX = bufferedImage.getWidth() - Neural_Network.picW;
        int loopY = bufferedImage.getHeight() - Neural_Network.picH;

        images = new Holder[loopX];

        // For each 28x28 possible sub image, create a sub image and test it with the network.
        for(int x = 0; x<loopX; x++){
            Holder brightest = new Holder(0,Double.MAX_VALUE);
            for(int y = 0; y<=loopY;y++){
                // Get sub image and run the network
                Holder tmp = nn.runNetwork(getSubImage(bufferedImage,x,y,Neural_Network.picH));
                // Checks if a better estimation is found within the same y coordinate.
                if(brightest.getHowClose()> tmp.getHowClose()) {
                    brightest = tmp;
                    brightest.setX(x);
                    brightest.setY(y);
                }
            }
            images[x] = brightest;
        }
        return removeOverlap();
    }
    // Cut out an image of a given size from a larger image. The cut is made from position x, y
    private BufferedImage getSubImage(BufferedImage bi,int x,int y,int size){
        BufferedImage ret = new BufferedImage(size,size,BufferedImage.TYPE_INT_RGB);
        Graphics g = ret.getGraphics();
        for(int i = 0; i<size;i++){
            for(int j = 0; j<size;j++){
                int c = bi.getRGB(i+x,j+y);
                g.setColor(new Color(c));
                g.fillRect(i,j,1,1);
            }
        }
        return ret;
    }
    // Checks which images that are overlapping.
    // Only keep the images with the lowest cost function and remove black images.
    private ArrayList<Holder> removeOverlap(){
        ArrayList<Holder> unique = new ArrayList<>();
        PriorityQueue<Holder> tmp = new PriorityQueue<>();

        /*Adds all images in images to the priorityqueue tmp, which place the image with the
          the lowest cost first*/

        for(Holder holder : images)
            tmp.add(holder);
        while (tmp.peek() != null){
            Holder holder = tmp.poll();
            if(holder.getSol() == 10)
                continue;
            boolean col = false;
            /*Checks if the image in the priorityqueue overlap with a placed image in unique.
              If the image collide it will be discarded*/
            for(Holder holder1 : unique){

                if (overlap(holder1.getX(), holder1.getY(), holder.getX(), holder.getY())) {
                  col = true;
                  break;
                }
            }
            //If the image do not overlap with any image in unique, add it to unique
            if(!col){
                unique.add(holder);
            }
        }
        //Sort the images by x coordinate, with the lowest x coordinate first.
        Collections.sort(unique,compareHolderX);
        for (Holder holder: unique){
            System.out.println(holder.getSol()+" "+holder.getHowClose()+" "+holder.getX());
        }
        return unique;

    }
    //checks if two images of size 28x28 overlap with each other
    private boolean overlap(int x1,int y1, int x2, int y2){
        if(x1 + Neural_Network.picW < x2  ){
            return false;
        }else if(x2 + Neural_Network.picW < x1 ){
            return false;
        }else if(y1 + Neural_Network.picH < y2 ){
            return false;
        }else if(y2 + Neural_Network.picH < y1 ){
            return false;
        }else {
            return true;
        }
    }

    //A comparator used to sort a Holder by x coordinate
    Comparator<Holder> compareHolderX = new Comparator<Holder>() {
        @Override
        public int compare(Holder o1, Holder o2) {
            if(o1.getX()<o2.getX())
                return -1;
            else if(o1.getX()>o2.getX()){
                return 1;
            }else {
                return 0;
            }
        }
    };
}
