package ImageTools;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReadImage {

    private String path;
    private File[] imagePathList;


    public ReadImage(String path){
        this.path = path;
    }
    private void createImageList(String path){
        File folder = new File(path);
        imagePathList = folder.listFiles();
    }

}
