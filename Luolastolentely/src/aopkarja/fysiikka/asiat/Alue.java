package aopkarja.fysiikka.asiat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Alue, joka saattaa jopa koostua monesta {@link Koordinaatti}sta.
 *
 * @author aopkarja
 */
public class Alue implements Cloneable {

    private List<Koordinaatti> koordinaatit;
    private Koordinaatti keskipiste;
    //Koska olemme laiskoja ja emme laske, jos ei ole muuttunut.
    private final boolean[] muutettu;
    private double sisalla = -1;
    private double ulkona = -1;
    private static final double TAU = Math.PI * 2;

    public Alue(Koordinaatti... koordinaatit) {
        this();
        this.koordinaatit.addAll(Arrays.asList(koordinaatit));
    }

    public Alue(List<Koordinaatti> alue) {
        this();
        this.koordinaatit.addAll(alue);
    }

    public Alue() {
        this.koordinaatit = new ArrayList<>();
        muutettu = new boolean[3];
        Arrays.fill(muutettu, true);
        keskipiste = new Koordinaatti();
    }

    /**
     * Lisää koordinaatin alueeseen.
     *
     * @param koordinaatti Koordinaatti
     */
    public void lisaa(Koordinaatti koordinaatti) {
        koordinaatit.add(koordinaatti);
        Arrays.fill(muutettu, true);
    }

    public List<Koordinaatti> getKoordinaatit() {
        return Collections.unmodifiableList(koordinaatit);
    }

    /**
     * Asettaa koordinaatit uudelleen.
     *
     * @param koordinaatit koordinaatit
     */
    public void setKoordinaatit(List<Koordinaatti> koordinaatit) {
        this.koordinaatit.clear();
        for (Koordinaatti k : koordinaatit) {
            this.koordinaatit.add(k.clone());
        }
        Arrays.fill(muutettu, true);
    }

    public Koordinaatti getKeskipiste() {
        if (muutettu[0]) {
            muutettu[0] = false;
            if (!koordinaatit.isEmpty()) {
                keskipiste.nollaa();
                for (Koordinaatti k : koordinaatit) {
                    keskipiste.siirra(k);
                }
                keskipiste.skaalaa(1.0 / koordinaatit.size());
            }
        }
        return keskipiste.clone();
    }

    public double getIsoimmanSisallaOlevanYmpyranSade() {
        if (muutettu[1] || sisalla == -1) {
            muutettu[1] = false;
            double min = Double.MAX_VALUE;
            for (Koordinaatti k : koordinaatit) {
                double cur = getKeskipiste().etaisyysToiseen(k);
                if (cur < min) {
                    min = cur;
                }
            }
            sisalla = Math.sqrt(min);
        }
        return sisalla;
    }

    public double getPienimmanUlkonaOlevanYmpyranSade() {
        if (muutettu[2] || ulkona == -1) {
            muutettu[2] = false;
            double max = 0;
            for (Koordinaatti k : koordinaatit) {
                double cur = getKeskipiste().etaisyysToiseen(k);
                if (cur > max) {
                    max = cur;
                }
            }
            ulkona = Math.sqrt(max);
        }
        return ulkona;
    }

    @Override
    public Alue clone() {
        try {
            Alue klooni = (Alue) super.clone();
            klooni.koordinaatit = new ArrayList(koordinaatit);
            klooni.keskipiste = keskipiste == null ? null : keskipiste.clone();
            return klooni;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

    /**
     * Siirtää aluetta koordinaattien verran
     *
     * @param koordinaatti koordinaatti
     */
    public void siirra(Koordinaatti koordinaatti) {
        for (Koordinaatti k : koordinaatit) {
            k.siirra(koordinaatti);
        }
        Arrays.fill(muutettu, true);
    }

    /**
     * Skaalaa aluettaa kertoimella
     *
     * @param kerroin kerroin
     */
    public void skaalaa(double kerroin) {
        ulkona *= kerroin;
        sisalla *= kerroin;
        Koordinaatti vastaKeskipiste = getKeskipiste().vastaKohta();
        for (Koordinaatti k : koordinaatit) {
            k.siirra(vastaKeskipiste);
            k.skaalaa(kerroin);
            k.siirra(keskipiste);
        }
    }

    /**
     * Kiertää aluetta asteiden määrittämässä tasossa tietyn prosenttimäärän
     * verran. Tämä tapahtuu niin, että jos katsomme tasoa aste1:den suuretessa
     * ylös ja aste2:den oikealle niin käännös tapahtuu positiivisessa
     * suunnannassa keskipisteen ympäri vastapäivään.
     *
     * @param maaraProsentteina Kuinka monta prosenttia käännetään
     * @param aste1 aste
     * @param aste2 aste
     */
    public void kierra(double maaraProsentteina, int aste1, int aste2) {
        if (maaraProsentteina == 0 || maaraProsentteina == 1) {
            return;
        }

        Koordinaatti keski = getKeskipiste();
        Koordinaatti vastaKeskipiste = keski.vastaKohta();

        int max = Math.max(aste1, aste2);
        boolean[] skippaa = new boolean[max + 1];
        Arrays.fill(skippaa, true);
        skippaa[aste1] = false;
        skippaa[aste2] = false;

        double kaanto = maaraProsentteina * TAU;
        for (Koordinaatti temp : koordinaatit) {
            Koordinaatti k = temp.getRajoitettu(skippaa);
            k.siirra(vastaKeskipiste);
            double uusiKulma = kaanto + Math.atan2(k.get(aste2), k.get(aste1));
            double sade = k.etaisyys(keski);
            k.set(Math.cos(uusiKulma) * sade, aste1);
            k.set(Math.sin(uusiKulma) * sade, aste2);
            k.siirra(keski);
        }
        Arrays.fill(muutettu, true);
    }

    /**
     * Lisää alueen koordinaatit alueeseen.
     *
     * @param alue Alue
     */
    public void lisaa(Alue alue) {
        for (Koordinaatti k : alue.getKoordinaatit()) {
            koordinaatit.add(k);
        }
        Arrays.fill(muutettu, true);
    }

    @Override
    public String toString() {
        String kirjainjono = "Alue: " + koordinaatit + "\n";
        kirjainjono += "\t Keskipiste: " + keskipiste + ":" + muutettu[0] + "\n";
        kirjainjono += "\t Sisalla: " + Math.round(sisalla * 100) / 100.0 + ":" + muutettu[1] + "\n";
        kirjainjono += "\t Ulkona: " + Math.round(ulkona * 100) / 100.0 + ":" + muutettu[2];

        return kirjainjono;
    }

    /**
     * Siirtää alueen niin, että keskipiste siirtyy koordinaattiin.
     *
     * @param koordinaatti uusi keskipiste
     */
    public void muuta(Koordinaatti koordinaatti) {
        Koordinaatti vastaKeskipiste = getKeskipiste().vastaKohta();
        for (Koordinaatti k : koordinaatit) {
            k.siirra(vastaKeskipiste);
            k.siirra(koordinaatti);
        }
        Arrays.fill(muutettu, true);
    }
}
