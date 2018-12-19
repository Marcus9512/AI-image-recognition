package ImageTools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadImage {

    private File[] imagePathList;
    private int currentImage = 0;

    public ReadImage(String path){
        createImageList(path);
    }
    private void createImageList(String path){
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(classLoader.getResource(path).getFile());

        imagePathList = folder.listFiles();
        if(imagePathList == null){
            System.err.println("Could not find files in this path "+path);
        }
    }
    public BufferedImage getNextImage(){
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
    public void reset(){
        currentImage = 0;
    }
    public boolean hasNext(){
        return currentImage < imagePathList.length ? true : false;
    }

}
