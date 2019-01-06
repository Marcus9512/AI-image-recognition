package ImageTools;

import java.awt.image.BufferedImage;

public class Dataset {
    /**
     * This is a container of information that is passed between NN and ReadImage
     * image contains the image
     * solution contains an int which represents the solution or "label" to the image
     * search contains the search path for the image
     */
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
