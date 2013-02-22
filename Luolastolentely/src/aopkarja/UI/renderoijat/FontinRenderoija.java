package aopkarja.UI.renderoijat;

import aopkarja.UI.Renderoija;
import java.io.File;

/**
 *
 * @author aopkarja
 */
public class FontinRenderoija extends Renderoija {

    private final File fontti;

    public FontinRenderoija(String fileName) {
        this.fontti = new File(fileName);
    }

    public File getFontti() {
        return fontti;
    }

    public void renderoiChar(char ch) {
    }

    /**
     * Muuttaa merkin indeksiksi fontissa.
     * 
     * @param merkki merkki
     */
    public int getIndeksiCharille(char merkki) {
        int indeksi;
        if (Character.isDigit(merkki)) {
            return 32 * 2 + "0123456789".indexOf(merkki);
        } else if (Character.isAlphabetic(merkki)) {
            indeksi = "abcdefghijklmnopqrstuvwxyzåäö".indexOf(merkki);
            if (indeksi == -1) {
                return 32 + "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ".indexOf(merkki);
            }
            return indeksi;
        }else{
            return 32 * 2 + 11 + "()\"\',.!?-_:;".indexOf(merkki);
        }
    }
}
