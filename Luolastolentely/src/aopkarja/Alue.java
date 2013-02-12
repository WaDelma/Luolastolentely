package aopkarja;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean[] muutettu;
    private double sisalla = -1;
    private double ulkona = -1;

    @Override
    public String toString() {
        String kirjainjono = "Alue: " + koordinaatit + " ";
        kirjainjono += keskipiste + ":" + muutettu[0] + " ";
        kirjainjono += sisalla + ":" + muutettu[1] + " ";
        kirjainjono += ulkona + ":" + muutettu[2];
        
        return  kirjainjono;
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
        for (Koordinaatti koordinaatti : koordinaatit) {
            this.koordinaatit.add(koordinaatti.clone());
        }

    }

    public Koordinaatti getKeskipiste() {
        if (muutettu[0] || keskipiste == null) {
            muutettu[0] = false;
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
        if (muutettu[1] || sisalla == -1) {
            muutettu[1] = false;
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
        if (muutettu[2] || ulkona == -1) {
            muutettu[2] = false;
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
}
