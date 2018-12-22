package ImageTools;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ReadImage {

    private ArrayList<Dataholder> imagePathList = new ArrayList<>();
    private int currentImage = 0;

    public ReadImage(String path){
        createImageList(path);
    }
    private void createImageList(String path){
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(classLoader.getResource(path).getFile());


        File[] file1 = folder.listFiles();
        for(int i = 0 ; i < file1.length ; i++){
            File[] file2 = file1[i].listFiles();
            for(int j = 0 ; j <file2.length;j++){
                imagePathList.add(new Dataholder(file2[j],i));
            }
        }
        Collections.shuffle(imagePathList);

        if(imagePathList == null){
            System.err.println("Could not find files in this path "+path);
        }

    }
    public Dataset getNext(){
        Dataholder tmp;
        if(currentImage < imagePathList.size()) {
            tmp = imagePathList.get(currentImage);
            currentImage++;
        }else{
            return null;
        }
        try {
            return new Dataset(ImageIO.read(tmp.file),tmp.id,tmp.file.getPath());

        } catch (IOException e) {
            System.err.println("File not found");
        }
        return null;
    }
    public void reset(){
        currentImage = 0;
        Collections.shuffle(imagePathList);
    }
    public boolean hasNext(){
        return currentImage < imagePathList.size() ? true : false;
    }
    private class Dataholder{
        File file;
        int id;

        public Dataholder(File file, int id) {
            this.file = file;
            this.id = id;
        }
    }

}
