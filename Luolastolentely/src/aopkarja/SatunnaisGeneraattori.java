package aopkarja;

import java.util.Random;

/**
 *
 * @author Antti
 */
public class SatunnaisGeneraattori {

    private static final Random satunnainen = new Random();

    public static boolean getBoolean() {
        return satunnainen.nextBoolean();
    }

    public static int getInt(int pienin, int suurinMiinusYksi) {
        return pienin + satunnainen.nextInt(suurinMiinusYksi - pienin);
    }
    
    public static double getDouble(){
        return satunnainen.nextDouble();
    }
}
