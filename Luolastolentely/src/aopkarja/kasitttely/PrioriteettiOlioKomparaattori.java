package aopkarja.kasitttely;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author aopkarja
 */
class PrioriteettiOlioKomparaattori implements Serializable, Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return getPrioriteetti(o1) - getPrioriteetti(o2);
    }

    public int getPrioriteetti(Object o) {
        int prioriteetti = 0;
        if (o.getClass().isAnnotationPresent(Prioriteetti.class)) {
            prioriteetti = o.getClass().getAnnotation(Prioriteetti.class).value();
        }
        return prioriteetti;
    }
}
