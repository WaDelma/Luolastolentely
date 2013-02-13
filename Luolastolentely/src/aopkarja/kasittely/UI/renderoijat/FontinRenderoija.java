package aopkarja.kasittely.UI.renderoijat;

import aopkarja.Komponentti;
import aopkarja.kasittely.UI.Renderoija;
import java.io.File;

/**
 *
 * @author aopkarja
 */
public class FontinRenderoija implements Renderoija {

    private final File file;

    public FontinRenderoija(String fileName) {
        this.file = new File(fileName);
    }

    public File getFile() {
        return file;
    }

    public void renderoiChar(char ch) {
    }

    public int getIndeksiCharille(char ch) {
        int index;
        if (Character.isDigit(ch)) {
            return 32 * 2 + "0123456789".indexOf(ch);
        } else if (Character.isAlphabetic(ch)) {
            index = "abcdefghijklmnopqrstuvwxyzåäö".indexOf(ch);
            if (index == -1) {
                return 32 + "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ".indexOf(ch);
            }
            return index;
        }else{
            return 32 * 2 + 11 + "()\"\',.!?-_:;".indexOf(ch);
        }
    }

    @Override
    public void renderoi(Komponentti komponentti) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initialisoi(Komponentti komponentti) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
