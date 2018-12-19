package ImageTools;

import java.awt.image.BufferedImage;

public class Dataset {
    private BufferedImage image;
    private int solution;

    public Dataset(BufferedImage image, int solution) {
        this.image = image;
        this.solution = solution;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getSolution() {
        return solution;
    }
}
