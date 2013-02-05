package aopkarja.hoitajat;

/**
 * Auttaa virheiden hoitamisessa
 * 
 * @author aopkarja
 */
public class VirheidenHoitaja {

    public static void ilmoita(Exception e) {
        e.printStackTrace();
    }

    private VirheidenHoitaja() {
    }
}
