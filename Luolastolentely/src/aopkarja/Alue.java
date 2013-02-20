package aopkarja;

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

    public Alue(List<Koordinaatti> temp) {
        this.koordinaatit = new ArrayList<>(temp);
        muutettu = new boolean[3];
    }

    public Alue() {
        this.koordinaatit = new ArrayList<>();
        muutettu = new boolean[3];
    }

    public void lisaa(Koordinaatti koordinaatti) {
        Arrays.fill(muutettu, true);
        koordinaatit.add(koordinaatti);
    }

    public List<Koordinaatti> getKoordinaatit() {
        return Collections.unmodifiableList(koordinaatit);
    }

    public void setKoordinaatit(List<Koordinaatti> koordinaatit) {
        Arrays.fill(muutettu, true);
        this.koordinaatit.clear();
        for (Koordinaatti k : koordinaatit) {
            this.koordinaatit.add(k.clone());
        }
    }

    public Koordinaatti getKeskipiste() {
        if (muutettu[0] || keskipiste == null) {
            muutettu[0] = false;
            keskipiste = new Koordinaatti();
            if (!koordinaatit.isEmpty()) {
                for (Koordinaatti k : koordinaatit) {
                    keskipiste.siirra(k);
                }
                keskipiste.kerro(1.0 / koordinaatit.size());
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

    public void siirra(Koordinaatti koordinaatti) {
        Arrays.fill(muutettu, true);
        for (Koordinaatti k : koordinaatit) {
            k.siirra(koordinaatti);
        }
    }

    public void siirra(double... koordinaatti) {
        Arrays.fill(muutettu, true);
        for (Koordinaatti k : koordinaatit) {
            k.siirra(koordinaatti);
        }
    }

    public void skaalaa(double kerroin) {
        ulkona *= kerroin;
        sisalla *= kerroin;
        Koordinaatti vastaKeskipiste = getKeskipiste().vastaKohta();
        for (Koordinaatti k : koordinaatit) {
            k.siirra(vastaKeskipiste);
            k.kerro(kerroin);
            k.siirra(keskipiste);
        }
    }

    public void kierra(double maaraProsentteina, int aste1, int aste2) {
        Koordinaatti keski =  getKeskipiste();
        Koordinaatti vastaKeskipiste = keski.vastaKohta();
        
        int max = Math.max(aste1, aste2);
        boolean[] skippaa = new boolean[max + 1];
        Arrays.fill(skippaa, true);
        skippaa[aste1] = false;
        skippaa[aste2] = false;
        
        double kaanto = maaraProsentteina * TAU;
        for (Koordinaatti k : koordinaatit) {
            k.siirra(vastaKeskipiste, skippaa);
            double[] temp = k.getKoordinaatti();
            double uusiKulma = kaanto + Math.atan2(temp[aste2], temp[aste1]);
            double sade = k.etaisyys(keski, skippaa);
            double[] uusi = new double[max + 1];
            uusi[aste1] = Math.cos(uusiKulma) * sade;
            uusi[aste2] = Math.sin(uusiKulma) * sade;
            k.muuta(keski, skippaa);
            k.siirra(uusi);
        }
    }

    public void lisaa(Alue alue) {
        for (Koordinaatti k : alue.getKoordinaatit()) {
            koordinaatit.add(k);
        }
    }

    @Override
    public String toString() {
        String kirjainjono = "Alue: " + koordinaatit + "\n";
        kirjainjono += "\t Keskipiste: " + keskipiste + ":" + muutettu[0] + "\n";
        kirjainjono += "\t Sisalla: " + Math.round(sisalla * 100) / 100.0 + ":" + muutettu[1] + "\n";
        kirjainjono += "\t Ulkona: " + Math.round(ulkona * 100) / 100.0 + ":" + muutettu[2];

        return kirjainjono;
    }
}
