package ImageTools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReadImage {

    private File[] imagePathList;
    private int currentImage = 0;

    public ReadImage(String path){
        createImageList(path);
    }
    private void createImageList(String path){
        File folder = new File(path);
        imagePathList = folder.listFiles();
    }
    private BufferedImage getNextImage(){
        File tmp;
        if(currentImage < imagePathList.length) {
            tmp = imagePathList[currentImage];
            currentImage++;
        }else{
            return null;
        }
        try {
            return ImageIO.read(tmp);

        } catch (IOException e) {
            System.err.println("File not found");
        }
        return null;
    }

}
