package aopkarja.kasitttely;

import aopkarja.kasitttely.KasittelynHoitaja.KasittelijaKapseli;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author aopkarja
 */
public class PrioriteettiKomparaattori implements Serializable, Comparator<KasittelijaKapseli> {

    @Override
    public int compare(KasittelijaKapseli o1, KasittelijaKapseli o2) {
        return o2.getPrioriteetti() - o1.getPrioriteetti();
    }
}
