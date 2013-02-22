package aopkarja.hoitajat;

import java.util.Random;

/**
 * Hoitaa satunnaisuutta
 *
 * @author Antti
 */
public class SatunnaisuudenHoitaja {

    private static final Random satunnainen = new Random();

    /**
     * @return Satunnainen totuusarvo
     */
    public static boolean getBoolean() {
        return satunnainen.nextBoolean();
    }

    /**
     * Palauttaa satunnaisen kokonaisluvun kahden kokonaisluvun väliltä
     * 
     * @param pienin
     * @param suurinMiinusYksi
     * @return Satunnainen kokonaisluku
     */
    public static int getInt(int pienin, int suurinMiinusYksi) {
        return pienin + satunnainen.nextInt(suurinMiinusYksi - pienin);
    }

    /**
     * @return Satunnainen tuplatarkka liukuluku
     */
    public static double getDouble() {
        return satunnainen.nextDouble();
    }

    private SatunnaisuudenHoitaja() {
    }
}
