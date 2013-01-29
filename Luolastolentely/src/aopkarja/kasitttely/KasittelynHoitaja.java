package aopkarja.kasitttely;

import aopkarja.hoitajat.VirheidenHoitaja;
import aopkarja.kasittely.tapahtumat.TyhjaTapahtuma;
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
    private static final Tapahtuma tyhjaTapahtuma = new TyhjaTapahtuma();

    public List<T> getKasittelijat() {
        return kasittelijaOliot;
    }

    public KasittelynHoitaja() {
        this.kasittelijaOliot = new ArrayList<>();
        kasittelijat = new HashMap<>();
    }

    public void kasittele(KasittelyTyyppi tyyppi) {
        kasittele(kasittelijat.get(tyyppi), tyhjaTapahtuma);
    }

    public void kasittele(KasittelyTyyppi tyyppi, Tapahtuma tapahtuma) {
        kasittele(kasittelijat.get(tyyppi), tapahtuma);
    }

    public void kasittele(KasittelyTyyppi tyyppi, T kasittelija) {
        kasittele(tyyppi, kasittelija, tyhjaTapahtuma);
    }

    public void kasittele(KasittelyTyyppi tyyppi, T kasittelija, Tapahtuma tapahtuma) {
        if (kasittelijat == null || kasittelijat.isEmpty()) {
            return;
        }
        List<KasittelijaKapseli> lista = kasittelijat.get(tyyppi);     
        if(lista == null){
            return;
        }
        for (KasittelijaKapseli kapseli : lista) {
            if (kasittelija == null || kasittelija.equals(kapseli.olio)) {
                kapseli.kayta(tapahtuma);
            }
        }
    }

    public void kasittele(List<KasittelijaKapseli> kasittelijat, Tapahtuma tapahtuma) {
        if (kasittelijat == null || kasittelijat.isEmpty()) {
            return;
        }
        for (KasittelijaKapseli kapseli : kasittelijat) {
            kapseli.kayta(tapahtuma);
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
        private boolean tapahtumiaKasitteleva;

        public KasittelijaKapseli(T olio, Method metodi, int prioriteetti) {
            this.olio = olio;
            this.metodi = metodi;
            this.prioriteetti = prioriteetti;
            Class<?>[] parametrit = metodi.getParameterTypes();
            if (parametrit.length > 1) {
                throw new IllegalArgumentException("Wrong amount of parameters (" + parametrit.length + ") in method " + metodi.getName());
            } else if (parametrit.length == 1) {
                if (parametrit[0].equals(Tapahtuma.class)) {
                    tapahtumiaKasitteleva = true;
                } else {
                    throw new IllegalArgumentException("Method with no parameters or with parameter type of Tapahtuma expected. Not type of " + parametrit[0].getName());
                }
            }
        }

        public void kayta(Tapahtuma tapahtuma) {
            try {
                if (tapahtumiaKasitteleva) {
                    metodi.invoke(olio, tapahtuma);
                } else {
                    metodi.invoke(olio);
                }
            } catch (ReflectiveOperationException e) {
                System.out.println(metodi);
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
