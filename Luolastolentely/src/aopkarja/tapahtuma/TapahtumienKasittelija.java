package aopkarja.tapahtuma;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aopkarja
 */
public class TapahtumienKasittelija {

    private List<Tapahtuma> tapahtumat;

    public TapahtumienKasittelija() {
        tapahtumat = new ArrayList<>();
    }

    public void lisaa(Tapahtuma tapahtuma) {
        tapahtumat.add(tapahtuma);
    }

    public void kasittele(List kasittelijat) {
        
    }
}
