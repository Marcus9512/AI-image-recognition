package ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TestImageClass {
    public static void main(String[] args) {
        ReadImage test = new ReadImage("bilder");
        BufferedImage bi;
        while ((bi = test.getNextImage()) != null){
            int w = bi.getWidth();
            int h = bi.getHeight();
            int c = bi.getRGB(w/2,h/2);
            Color color = new Color(c);
            System.out.println(color.getRed()+" "+color.getGreen()+" "+color.getBlue());
        }

    }
}
