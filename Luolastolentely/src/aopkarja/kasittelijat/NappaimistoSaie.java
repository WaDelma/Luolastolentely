package aopkarja.kasittelijat;

import aopkarja.kasittely.tapahtumat.NappaimistoTapahtuma;
import aopkarja.kasittely.tapahtumat.Painallus;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

/**
 * Käsittelee näppäimistöä
 *
 * @author aopkarja
 */
public class NappaimistoSaie extends SisaantuloSaie {

    public NappaimistoSaie(TapahtumienKasittelija kasittelija) {
        super(kasittelija);
    }

    @Override
    public void initialisoi() throws LWJGLException {
        Keyboard.create();
    }

    @Override
    public void looppi() throws LWJGLException {
        Keyboard.poll();

        while (Keyboard.next()) {
            int painike = Keyboard.getEventCharacter();
            boolean painettu = Keyboard.getEventKeyState();
            getKasittelija().lisaa(new Painallus(new NappaimistoTapahtuma(), getPainike(painike), painettu));
            //System.out.println((int) painike);
            //System.out.println(painike);
//            if (painike == -1) {
//                continue;
//            }
//            boolean painettu = Mouse.getEventButtonState();
//            if (painettu && !painikkeidenTilat[painike]) {
//                Koordinaatti koordinaatit = new Koordinaatti(Mouse.getEventX(), Mouse.getEventY());
//                getKasittelija().lisaa(new Painallus(getPainike(painike), koordinaatit));
//            }
//            if (!painettu && painikkeidenTilat[painike]) {
//                Koordinaatti koordinaatit = new Koordinaatti(Mouse.getEventX(), Mouse.getEventY());
//                getKasittelija().lisaa(new PainalluksenPaasto(getPainike(painike), koordinaatit));
//            }
//            painikkeidenTilat[painike] = painettu;
        }
    }

    @Override
    public void lopeta() {
        Keyboard.destroy();
    }

    /**
     *
     * @param painike
     * @return Painikkeen muuttumaton koodi muuttuvasta
     */
    public static int getPainike(int painike) {
        return painike;
    }
}
