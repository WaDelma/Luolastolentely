package aopkarja.fysiikka.asiat;

import java.util.Arrays;

/**
 * Koordinaatti n:ssä asteessa.
 *
 * @author Antti
 */
public class Koordinaatti implements Cloneable {

    double[] koordinaatti;
    boolean[] skippaa;

    /**
     * Uusi koordinaatti taulukosta
     *
     * @param koordinaatti
     */
    public Koordinaatti(double... koordinaatti) {
        this.koordinaatti = koordinaatti.clone();
        this.skippaa = new boolean[koordinaatti.length];
    }

    /**
     * Uusi koordinaatti toisesta koordinaatista
     *
     * @param koordinaatti
     */
    public Koordinaatti(Koordinaatti koordinaatti) {
        this.koordinaatti = koordinaatti.koordinaatti.clone();
        this.skippaa = koordinaatti.skippaa.clone();
    }

    /**
     * Tyhjä koordinaatti
     */
    public Koordinaatti() {
        koordinaatti = new double[1];
        skippaa = new boolean[1];
    }

    private Koordinaatti(Koordinaatti koordinaatti, boolean[] skippaa) {
        this.koordinaatti = koordinaatti.koordinaatti;
        this.skippaa = skippaa.clone();
    }

    /**
     * Siirtää koordinaatin parametrin koordinaattiin
     *
     * @param koordinaatti
     */
    public void muuta(Koordinaatti koordinaatti) {
        varmistaMahtuminen(koordinaatti.koordinaatti.length - 1);
        for (int n = 0; n < koordinaatti.koordinaatti.length; n++) {
            if (!skippaa[n]) {
                this.koordinaatti[n] = koordinaatti.koordinaatti[n];
            }
        }
    }

    /**
     * Siirtää koordinaattia parametrin koordinaatin verran
     *
     * @param koordinaatti
     */
    public void siirra(Koordinaatti koordinaatti) {
        varmistaMahtuminen(koordinaatti.koordinaatti.length - 1);
        for (int n = 0; n < koordinaatti.koordinaatti.length; n++) {
            if (!skippaa[n]) {
                this.koordinaatti[n] += koordinaatti.koordinaatti[n];
            }
        }
    }

    /**
     *
     * @return Etäisyys origosta
     */
    public double etaisyysOrigosta() {
        double result = 0;
        for (int n = 0; n < koordinaatti.length; n++) {
            if (!skippaa[n]) {
                result += koordinaatti[n] * koordinaatti[n];
            }
        }
        return Math.sqrt(result);
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys koordinaatista
     */
    public double etaisyys(Koordinaatti koordinaatti) {
        return Math.sqrt(etaisyysToiseen(koordinaatti));
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys toiseeen koordinaatista
     */
    public double etaisyysToiseen(Koordinaatti koordinaatti) {
        varmistaMahtuminen(koordinaatti.koordinaatti.length - 1);
        double result = 0;
        for (int n = 0; n < koordinaatti.koordinaatti.length; n++) {
            if (!skippaa[n]) {
                double temp = koordinaatti.koordinaatti[n]
                        - this.koordinaatti[n];
                result += temp * temp;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return 83 * 3 + Arrays.hashCode(this.koordinaatti);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.koordinaatti, ((Koordinaatti) o).koordinaatti);
    }

    /**
     *
     * @return asteen arvo
     */
    public double get(int aste) {
        varmistaMahtuminen(aste);
        return koordinaatti[aste];
    }

    @Override
    public Koordinaatti clone() {
        try {
            Koordinaatti klooni = (Koordinaatti) super.clone();
            System.arraycopy(this.koordinaatti, 0, klooni.koordinaatti, 0, this.koordinaatti.length);
            return klooni;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (double d : koordinaatti) {
            builder.append(Math.round(d * 100) / 100.0);
            builder.append(", ");
        }
        int uusiPituus = builder.length() - 2;
        if (uusiPituus > 0) {
            builder.setLength(uusiPituus);
        }
        builder.append(']');
        return builder.toString();
    }

    /**
     * @return Koordinaatti, jonka kaikkien asteiden arvot ovat vastalukuja
     */
    public Koordinaatti vastaKohta() {
        Koordinaatti result = new Koordinaatti(koordinaatti);
        for (int n = 0; n < koordinaatti.length; n++) {
            if (!skippaa[n]) {
                result.koordinaatti[n] = -result.koordinaatti[n];
            }
        }
        return result;
    }

    /**
     * Skaalaa koordinaattia kertoimella
     *
     * @param kerroin
     */
    public void skaalaa(double kerroin) {
        for (int n = 0; n < koordinaatti.length; n++) {
            if (!skippaa[n]) {
                koordinaatti[n] *= kerroin;
            }
        }
    }

    /**
     * Siirtää koordinaatin origoon
     */
    public void nollaa() {
        for (int n = 0; n < koordinaatti.length; n++) {
            if (!skippaa[n]) {
                koordinaatti[n] = 0;
            }
        }
    }

    /**
     * Tekee koordinaatista yksikkö vektorin
     */
    public void yksikoi() {
        double etaisyys = etaisyysOrigosta();
        for (int n = 0; n < koordinaatti.length; n++) {
            if (!skippaa[n]) {
                koordinaatti[n] /= etaisyys;
            }
        }
    }

    /**
     * Laskee pistetulon
     *
     * @param koordinaatti
     * @return Piste tulo koordinaatin kanssa
     */
    public double pisteTulo(Koordinaatti koordinaatti) {
        varmistaMahtuminen(koordinaatti.koordinaatti.length - 1);
        double vastaus = 0;
        for (int n = 0; n < koordinaatti.koordinaatti.length; n++) {
            if (!skippaa[n]) {
                vastaus += koordinaatti.koordinaatti[n] * this.koordinaatti[n];
            }
        }
        return vastaus;
    }

    /**
     * Koordinaatti origossa.
     */
    public static Koordinaatti getOrigo() {
        return new Koordinaatti();
    }

    /**
     * Asettaa koordinaatin arvon tietyssä asteessa
     *
     * @param arvo Arvo
     * @param aste aste
     */
    public void set(double arvo, int aste) {
        varmistaMahtuminen(aste);
        if (!skippaa[aste]) {
            koordinaatti[aste] = arvo;
        }
    }

    private void varmistaMahtuminen(int aste) {
        if (aste >= koordinaatti.length) {
            double[] tempKoordinaatti = new double[aste + 1];
            System.arraycopy(koordinaatti, 0, tempKoordinaatti, 0, koordinaatti.length);
            koordinaatti = tempKoordinaatti;
        }
        if (aste >= skippaa.length) {
            boolean[] tempSkippaa = new boolean[aste + 1];
            System.arraycopy(skippaa, 0, tempSkippaa, 0, skippaa.length);
            skippaa = tempSkippaa;
        }
    }
    
    /**
     * Mahdollistaa koordinaatin metodien rajaamisen tiettyihin asteisiin
     *
     * @param skippaa taulukko, joka määrittelee mitkä asteet skipataan
     * @return rahoitettu versio koordinaatista
     */

    public Koordinaatti getRajoitettu(boolean[] skippaa) {
        return new Koordinaatti(this, skippaa);
    }
}
