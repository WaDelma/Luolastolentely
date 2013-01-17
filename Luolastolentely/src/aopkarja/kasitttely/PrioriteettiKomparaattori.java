package aopkarja.kasitttely;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author aopkarja
 */
class PrioriteettiKomparaattori implements Serializable, Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return getPrioriteetti(o2) - getPrioriteetti(o1);
    }

    public int getPrioriteetti(Object o) {
        Prioriteetti annotaatio = o.getClass().getAnnotation(Prioriteetti.class);
        return annotaatio == null ? 0 : annotaatio.value();
    }
}
