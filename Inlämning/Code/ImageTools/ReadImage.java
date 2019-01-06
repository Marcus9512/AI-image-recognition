package ImageTools;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ReadImage {

    /**
     *  This class reads all images in a directory with subdirectories.
     *  The images is then returned as data sets in randomized order via getNext().
     *
     *  When there are no files left to read, null is returned.
     *  The method reset() shuffles imagePathlist and resets the file pointer to the beginning of the list.
     */
    private ArrayList<Dataholder> imagePathList = new ArrayList<>();
    private int currentImage = 0;

    public ReadImage(String path){
        createImageList(path);
    }
    //Loads all files from path and stores them in imagePathList, the order in the list is random
    private void createImageList(String path){
        File folder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"/"+path);
        //File folder = new File(path);


        File[] file1 = folder.listFiles();
        System.out.println(Arrays.toString(file1));
        for(int i = 0 ; i < file1.length ; i++){
            // Open subdirectories
            File[] file2 = file1[i].listFiles();
            /* For each image, store the file and the solution 'i' in imagePathList */
            for(int j = 0 ; j <file2.length;j++){
                imagePathList.add(new Dataholder(file2[j],i));
            }
        }
        // Shuffle the list
        Collections.shuffle(imagePathList);

        if(imagePathList == null){
            System.err.println("Could not find files in this path "+path);
        }

    }
    /*  Returns a next dataset from imagePathList,
        the file in imagePathList is converted to a BufferedImage and stored with its id in a data holder. */
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
    // Dataholder is used to store the file and solution
    private class Dataholder{
        File file;
        int id;

        public Dataholder(File file, int id) {
            this.file = file;
            this.id = id;
        }
    }

}
