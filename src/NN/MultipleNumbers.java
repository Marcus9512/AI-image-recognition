package NN;

import Tools.Holder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class MultipleNumbers {
    Holder[] images ;
    public ArrayList<Holder> analyzeImage(BufferedImage bufferedImage, Neural_Network nn){
        int loopX = bufferedImage.getWidth() - Neural_Network.picW;
        int loopY = bufferedImage.getHeight() - Neural_Network.picH;

        images = new Holder[loopX];

        for(int x = 0; x<loopX; x++){
            Holder brightest = new Holder(0,Double.MAX_VALUE);
            for(int y = 0; y<=loopY;y++){
                Holder tmp = nn.runNetwork(getSubImage(bufferedImage,x,y,Neural_Network.picH));
                if(brightest.getHowClose()> tmp.getHowClose()) {
                  //  System.out.println("True");
                    brightest = tmp;
                    brightest.setX(x);
                    brightest.setY(y);
                }
            }
            images[x] = brightest;
        }
        return removeOverlap();
    }
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
    private ArrayList<Holder> removeOverlap(){
        ArrayList<Holder> unique = new ArrayList<>();
        PriorityQueue<Holder> tmp = new PriorityQueue<>();

        for(Holder holder : images)
            tmp.add(holder);
        while (tmp.peek() != null){
            Holder holder = tmp.poll();
            if(holder.getSol() == 10)
                continue;
            boolean col = false;
            for(Holder holder1 : unique){

                if (overlap(holder1.getX(), holder1.getY(), holder.getX(), holder.getY())) {
                  col = true;
                  break;
                }
            }
            if(!col){
                unique.add(holder);
            }
        }
        Collections.sort(unique,compareHolderX);
        for (Holder holder: unique){
            System.out.println(holder.getSol()+" "+holder.getHowClose()+" "+holder.getX());
        }
        return unique;

    }
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
    Comparator<Holder> compareHolder = new Comparator<Holder>() {
        @Override
        public int compare(Holder o1, Holder o2) {
            if(o1.getHowClose()<o2.getHowClose())
                return -1;
            else if(o1.getHowClose()>o2.getHowClose()){
                return 1;
            }else {
                return 0;
            }
        }
    };
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
