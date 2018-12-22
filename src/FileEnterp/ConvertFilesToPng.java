package FileEnterp;

import java.io.*;

public class ConvertFilesToPng {

    private void createImageList(String path){
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(classLoader.getResource(path).getFile());

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(folder));
            int tmp;
            while ((tmp = bufferedReader.read()) != -1){
                System.out.println(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ConvertFilesToPng convertFilesToPng = new ConvertFilesToPng();
        convertFilesToPng.createImageList("train-images-idx3-ubyte.gz");
    }
}
