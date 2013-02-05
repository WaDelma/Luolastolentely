package aopkarja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Alue, joka saattaa jopa koostua monesta {@link Koordinaatti}sta.
 * @author aopkarja
 */
public class Alue implements Cloneable {

    private List<Koordinaatti> koordinaatit;
    private Koordinaatti keskipiste;
    //Koska olemme laiskoja ja emme laske, jos ei ole muuttunut.
    private int muutettu;
    private double sisalla = -1;
    private double ulkona = -1;

    public Alue() {
        this.koordinaatit = new ArrayList<>();
    }

    public void lisaa(Koordinaatti koordinaatti) {
        muutettu = 0b11111111;
        koordinaatit.add(koordinaatti);
    }

    public List<Koordinaatti> getKoordinaatit() {
        return Collections.unmodifiableList(koordinaatit);
    }

    public void setKoordinaatit(List<Koordinaatti> koordinaatit) {
        muutettu = 0b11111111;
        this.koordinaatit.clear();
        for (Koordinaatti koordinaatti : koordinaatit) {
            this.koordinaatit.add(koordinaatti.clone());
        }

    }

    public Koordinaatti getKeskipiste() {
        if ((muutettu & 0b00100000) == 0b00100000 || keskipiste == null) {
            muutettu &= 0b11011111;
            keskipiste = new Koordinaatti();
            for (Koordinaatti k : koordinaatit) {
                keskipiste.siirra(k);
            }
            double[] koord = keskipiste.getKoordinaatti();
            for (int n = 0; n < koord.length; n++) {
                koord[n] /= koordinaatit.size();
            }
        }
        return keskipiste;
    }

    public double getIsoimmanSisallaOlevanYmpyranSade() {
        if ((muutettu & 0b01000000) == 0b01000000 || sisalla == -1) {
            muutettu &= 0b10111111;
            double min = Double.MAX_VALUE;
            for (Koordinaatti koordinaatti : koordinaatit) {
                double cur = getKeskipiste().etaisyysToiseen(koordinaatti);
                if (cur < min) {
                    min = cur;
                }
            }
            sisalla = Math.sqrt(min);
        }
        return sisalla;
    }

    public double getPienimmanUlkonaOlevanYmpyranSade() {
        if ((muutettu & 0b10000000) == 0b10000000 || ulkona == -1) {
            muutettu &= 0b01111111;
            double max = 0;
            for (Koordinaatti koordinaatti : koordinaatit) {
                double cur = getKeskipiste().etaisyysToiseen(koordinaatti);
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
}
