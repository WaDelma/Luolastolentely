package aopkarja;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class Alue {
    
    private List<Koordinaatti> koordinaatit;
    
    private Koordinaatti keskipiste;

    public Alue() {
        this.koordinaatit = new ArrayList<>();
    }
    
    public void lisaa(Koordinaatti koordinaatti) {
        koordinaatit.add(koordinaatti);
        keskipiste = new Koordinaatti();
        for (Koordinaatti k : koordinaatit) {
            keskipiste.siirra(k);
        }
        int[] koord = keskipiste.getKoordinaatti();
        for (int n = 0; n < koord.length; n++) {
            koord[n] /= koordinaatit.size();
        }
    }

    public List<Koordinaatti> getKoordinaatit() {
        return koordinaatit;
    }
}
