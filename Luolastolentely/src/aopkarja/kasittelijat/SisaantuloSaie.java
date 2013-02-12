package aopkarja.kasittelijat;

import aopkarja.Luolastolentely;
import aopkarja.hoitajat.VirheidenHoitaja;
import org.lwjgl.LWJGLException;

/**
 * Säie, joka käsitelee sisääntuloa
 *
 * @author aopkarja
 */
public abstract class SisaantuloSaie extends Thread {

    private final TapahtumienKasittelija kasittelija;

    public SisaantuloSaie(TapahtumienKasittelija kasittelija) {
        this.kasittelija = kasittelija;
    }

    public final TapahtumienKasittelija getKasittelija() {
        return kasittelija;
    }

    public int getPollauksenAikavali() {
        return 10;
    }
    
    
    @Override
    public final void run() {
        try {
            initialisoi();
            while (Luolastolentely.getInstanssi().kaynnissa()) {
                looppi();
                try {
                    Thread.sleep(getPollauksenAikavali());
                } catch (InterruptedException ex) {
                }
            }
        } catch (LWJGLException ex) {
            VirheidenHoitaja.ilmoita(ex);
        } finally {
            lopeta();
        }
    }

    public abstract void initialisoi() throws LWJGLException;

    public abstract void looppi() throws LWJGLException;

    public abstract void lopeta();
}
