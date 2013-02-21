package aopkarja.hoitajat;

import java.util.Arrays;

/**
 *
 * @author aopkarja
 */
public class LokiHoitaja {

    public enum Vari {

        RESETOINTI("\u001B[0m"),
        MUSTA("\u001B[30m"),
        PUNAINEN("\u001B[31m"),
        VIHREA("\u001B[32m"),
        KELTAINEN("\u001B[33m"),
        SININEN("\u001B[34m"),
        PURPPURA("\u001B[35m"),
        SYAANI("\u001B[36m"),
        VALKOINEN("\u001B[37m");
        private String arvo;

        public String getArvo() {
            return arvo;
        }

        private Vari(String arvo) {
            this.arvo = arvo;
        }
    }
    private static boolean VARIT_PAALLA = true;

    public static void ilmoita(Object o) {
        StringBuilder rakentaja = new StringBuilder();
        asetaVari(rakentaja, Vari.SININEN);
        rakentaja.append(o);
        System.out.println(rakentaja);
    }
    
    /*
     * TODO:
     * 
     * 1. thrown exception: exception
     * Message: message
     *   ->  package.class.method(file:line)
     *   ->  package.class.method(file:line)
     *   2. thrown exception
     *   Message: message
     *      ->  package.class.method(file:line)
     * 
     * 
     * 
     */

    public static void ilmoita(Exception e) {
        StringBuilder rakentaja = new StringBuilder();
        asetaVari(rakentaja, Vari.PURPPURA);
        rakentaja.append("Error: ");
        rekursiivinenIlmoitus(rakentaja, e, 1, Vari.PUNAINEN);
        asetaVari(rakentaja, Vari.RESETOINTI);
        System.err.println(rakentaja);

       e.printStackTrace();
    }

    private static void rekursiivinenIlmoitus(StringBuilder rakentaja, Throwable e, int sisennys, Vari vari) {
        asetaVari(rakentaja, vari);
        lisaaUusiRivi(rakentaja.append(e.getClass().toString().substring(5)));
        String viesti = e.getMessage();
        if (viesti != null) {
            sisenna(rakentaja, sisennys - 1);
            asetaVari(rakentaja, Vari.PURPPURA);
            rakentaja.append("Message: ");
            asetaVari(rakentaja, vari);
            lisaaUusiRivi(rakentaja.append(e.getMessage()));
        }
        for (StackTraceElement elementti : e.getStackTrace()) {
            sisenna(rakentaja, sisennys);
            lisaaNuoli(rakentaja, vari);
            lisaaUusiRivi(rakentaja.append(elementti));
        }

        if (e.getSuppressed().length != 0) {
            sisenna(rakentaja, sisennys);
            asetaVari(rakentaja, Vari.PURPPURA);
            rakentaja.append("Suppressed: ");
            asetaVari(rakentaja, vari);
            lisaaUusiRivi(rakentaja);
            for (Throwable t : e.getSuppressed()) {
                sisenna(rakentaja, sisennys + 1);
                lisaaNuoli(rakentaja, vari);
                lisaaUusiRivi(rakentaja.append(t));
            }
        }

        if (e.getCause() != null) {
            sisenna(rakentaja, sisennys);
            asetaVari(rakentaja, Vari.PURPPURA);
            rakentaja.append("Cause:");
            rekursiivinenIlmoitus(rakentaja, e.getCause(), sisennys + 1, Vari.SININEN);
        }
    }

    private static void sisenna(StringBuilder rakentaja, int maara) {
        for (int n = 0; n < maara; n++) {
            rakentaja.append('\t');
        }
    }

    private static void lisaaUusiRivi(StringBuilder rakentaja) {
        rakentaja.append('\n');
    }

    private static void lisaaNuoli(StringBuilder rakentaja, Vari vari) {
        asetaVari(rakentaja, Vari.VIHREA);
        rakentaja.append(" ->");
        asetaVari(rakentaja, vari);
        rakentaja.append(' ');
    }

    private static void asetaVari(StringBuilder rakentaja, Vari vari) {
        if (VARIT_PAALLA) {
            rakentaja.append(vari.getArvo());
        }
    }
}
