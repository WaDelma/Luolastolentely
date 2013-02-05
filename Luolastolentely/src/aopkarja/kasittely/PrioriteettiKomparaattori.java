package aopkarja.kasittely;

import aopkarja.kasittely.KasittelynHoitaja.KasittelijaKapseli;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Järjestää {@link KasittelijaKapseli}t {@link Prioriteetti} järjestykseen
 * 
 * @author aopkarja
 */
class PrioriteettiKomparaattori implements Serializable, Comparator<KasittelijaKapseli> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(KasittelijaKapseli o1, KasittelijaKapseli o2) {
        return o2.getPrioriteetti() - o1.getPrioriteetti();
    }
}
