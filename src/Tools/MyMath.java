package Tools;

import java.util.Random;

public class MyMath {

    static Random random = new Random();

    // return a double between 0.0 and 1.0
    public static double rand(){
        return random.nextDouble();
    }

}
