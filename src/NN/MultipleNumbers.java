package NN;

import Tools.Holder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MultipleNumbers {
    Holder[] images ;
    public void analyzeImage(BufferedImage bufferedImage, Neural_Network nn){
        int loopX = bufferedImage.getWidth() - Neural_Network.picW;
        int loopY = bufferedImage.getHeight() - Neural_Network.picH;

        images = new Holder[loopX];

        for(int x = 0; x<loopX; x++){
            Holder brightest = new Holder(0,Double.MAX_VALUE);
            for(int y = 0; y<loopY;y++){
                Holder tmp = nn.runNetwork(getSubImage(bufferedImage,x,y,Neural_Network.picH));
                if(brightest.getHowClose()> tmp.getHowClose())
                    brightest = tmp;
            }
            images[x] = brightest;
        }
        for(int i = 0; i<images.length;i++){
            System.out.println(images[i].getSol()+" "+images[i].getHowClose());
        }
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
}
