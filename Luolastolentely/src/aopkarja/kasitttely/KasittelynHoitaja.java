package aopkarja.kasitttely;

import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.tapahtuma.TapahtumienKasittelija;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aopkarja
 */
public class KasittelynHoitaja<T> {

    private List<T> kasittelijaOliot;
    private Map<KasittelyTyyppi, List<KasittelijaKapseli>> kasittelijat;

    public List<T> getKasittelijat() {
        return kasittelijaOliot;
    }

    public KasittelynHoitaja() {
        this.kasittelijaOliot = new ArrayList<>();
        kasittelijat = new HashMap<>();
    }

    public void kasittele(KasittelyTyyppi tyyppi) {
        if (kasittelijat == null || kasittelijat.isEmpty()) {
            return;
        }
        for (KasittelijaKapseli kapseli : kasittelijat.get(tyyppi)) {
            kapseli.kayta();
        }
    }

    public void kasittele(KasittelyTyyppi tyyppi, T kasittelija) {
        if (kasittelijat == null || kasittelijat.isEmpty() || kasittelija == null) {
            return;
        }
        for (KasittelijaKapseli kapseli : kasittelijat.get(tyyppi)) {
            if (kasittelija.equals(kapseli.olio)) {
                kapseli.kayta();
            }
        }
    }

    public void lisaa(T kasittelija) {
        int prioriteetti = 0;
        Class<?> luokka = kasittelija.getClass();
        Prioriteetti prioriteettiAnnotaation = luokka.getAnnotation(Prioriteetti.class);
        if (prioriteettiAnnotaation != null) {
            prioriteetti = prioriteettiAnnotaation.value();
        }
        for (Method metodi : luokka.getMethods()) {
            Kasittelija annotaatio = metodi.getAnnotation(Kasittelija.class);
            if (annotaatio != null) {
                int valiaikainenPrioriteetti = prioriteetti;
                if (metodi.isAnnotationPresent(Prioriteetti.class)) {
                    valiaikainenPrioriteetti = metodi.getAnnotation(Prioriteetti.class).value();
                }
                lisaa(annotaatio.value(), new KasittelijaKapseli(kasittelija, metodi, valiaikainenPrioriteetti));
            }
        }
        kasittelijaOliot.add(kasittelija);
        Collections.sort(kasittelijaOliot, new PrioriteettiOlioKomparaattori());
    }

    private void lisaa(KasittelyTyyppi tyyppi, KasittelijaKapseli kapseli) {
        List<KasittelijaKapseli> kapselit = kasittelijat.get(tyyppi);
        if (kapselit == null) {
            kapselit = new ArrayList<>();
        }
        kapselit.add(kapseli);
        Collections.sort(kapselit, new PrioriteettiKomparaattori());
        kasittelijat.put(tyyppi, kapselit);
    }

    public List<T> getKasittelijat(Class luokka) {
        List<T> vastaus = new ArrayList<>();
        for (T o : kasittelijaOliot) {
            if (luokka.equals(o.getClass())) {
                vastaus.add(o);
            }
        }
        return vastaus;
    }

    public boolean lisatty(T moodi) {
        return kasittelijaOliot.contains(moodi);
    }

    public static class KasittelijaKapseli<T> {

        private final Method metodi;
        private final int prioriteetti;
        private final T olio;

        public KasittelijaKapseli(T olio, Method metodi, int prioriteetti) {
            this.olio = olio;
            this.metodi = metodi;
            this.prioriteetti = prioriteetti;
        }

        public void kayta() {
            try {
                metodi.invoke(olio);
            } catch (ReflectiveOperationException e) {
                VirheidenHoitaja.ilmoita(e);
            }
        }

        public int getPrioriteetti() {
            return prioriteetti;
        }

        @Override
        public String toString() {
            return olio.getClass().getName() + "." + metodi.getName() + ": " + prioriteetti;
        }
    }
}
