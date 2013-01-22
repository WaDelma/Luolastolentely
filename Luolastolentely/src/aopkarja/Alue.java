package aopkarja;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class Alue {
    
    private List<Koordinaatti> koordinaatit;

    public Alue() {
        this.koordinaatit = new ArrayList<>();
    }
    
    public void lisaa(Koordinaatti koordinaatti) {
        koordinaatit.add(koordinaatti);
    }
}
