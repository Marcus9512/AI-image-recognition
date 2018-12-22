package ImageTools;

import java.awt.image.BufferedImage;

public class Dataset {
    private BufferedImage image;
    private int solution;
    private String search;

    public Dataset(BufferedImage image, int solution,String search) {
        this.image = image;
        this.solution = solution;
        this.search = search;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getSolution() {
        return solution;
    }

    public String getSearch(){
        return search;
    }
}
