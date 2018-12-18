package Tools;

import java.util.Random;

public class MyMath {
    static Random random = new Random();
    public static int rand(int low, int high){
        if(low>=high)
            return low;
        return random.nextInt(high-low)+low;
    }

}
