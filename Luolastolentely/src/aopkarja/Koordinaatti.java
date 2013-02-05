package aopkarja;

import java.util.Arrays;

/**
 * Koordinaatti n:ssä asteessa.
 * @author Antti
 */
public class Koordinaatti implements Cloneable {

    private double[] koordinaatti;

    /**
     *
     * @param koordinaatit
     */
    public Koordinaatti(double... koordinaatit) {
        koordinaatti = koordinaatit.clone();
    }

    /**
     *
     * @param koordinaatti
     */
    public Koordinaatti(Koordinaatti koordinaatti) {
        this.koordinaatti = koordinaatti.koordinaatti.clone();
    }

    /**
     *
     */
    public Koordinaatti() {
        koordinaatti = new double[0];
    }

    /**
     *
     * @param koordinaatit
     */
    public void muuta(Koordinaatti koordinaatit) {
        muuta(koordinaatit.koordinaatti);
    }

    /**
     *
     * @param koordinaatit
     */
    public void muuta(double[] koordinaatit) {
        if (this.koordinaatti == null || koordinaatit.length >= this.koordinaatti.length) {
            this.koordinaatti = koordinaatit.clone();
            return;
        }
        System.arraycopy(koordinaatit, 0, this.koordinaatti, 0, koordinaatit.length);
    }

    /**
     *
     * @param koordinaatit
     */
    public void siirra(Koordinaatti koordinaatit) {
        siirra(koordinaatit.koordinaatti);
    }

    /**
     *
     * @param koordinaatit
     */
    public void siirra(double... koordinaatit) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new double[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatti.length) {
            double[] temp = new double[koordinaatit.length];
            System.arraycopy(this.koordinaatti, 0, temp, 0, this.koordinaatti.length);
            this.koordinaatti = temp;
        }
        for (int n = 0; n < koordinaatit.length; n++) {
            this.koordinaatti[n] += koordinaatit[n];
        }
    }

    /**
     *
     * @param koordinaatit
     * @return Etäisyys koordinaatista
     */
    public double etaisyys(Koordinaatti koordinaatit) {
        return etaisyys(koordinaatit.koordinaatti);
    }

    /**
     *
     * @param koordinaatit
     * @return Etäisyys toiseeen koordinaatista
     */
    public double etaisyysToiseen(Koordinaatti koordinaatit) {
        return etaisyysToiseen(koordinaatit.koordinaatti);
    }

    /**
     *
     * @param koordinaatit
     * @return Etäisyys koordinaatti listasta
     */
    public double etaisyys(double[] koordinaatit) {
        return Math.sqrt(etaisyysToiseen(koordinaatit));
    }

    /**
     *
     * @param koordinaatit
     * @return Etäisyys toiseeen koordinaatti listasta
     */
    public double etaisyysToiseen(double[] koordinaatit) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new double[koordinaatit.length];
        }
        if (koordinaatit.length > this.koordinaatti.length) {
            System.arraycopy(this.koordinaatti, 0, new double[koordinaatit.length], 0, this.koordinaatti.length);
        }
        double result = 0;
        for (int n = 0; n < koordinaatit.length; n++) {
            double temp = koordinaatit[n] - this.koordinaatti[n];
            result += temp * temp;
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
     * @return Kopio koordinaatti listasta
     */
    public double[] getKoordinaatti() {
        return koordinaatti.clone();
    }

    @Override
    public Koordinaatti clone() {
        try {
            Koordinaatti klooni = (Koordinaatti)super.clone();
            System.arraycopy(this.koordinaatti, 0, klooni.koordinaatti, 0, this.koordinaatti.length);
            return  klooni;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError();
        }
    }

    /**
     *
     * @param koordinaatti1
     * @param koordinaatti2
     * @param skippaa Lista skipattavista koordinaateista
     * @return Onko koordinaatti kahden välissä
     */
    public boolean valissako(Koordinaatti koordinaatti1, Koordinaatti koordinaatti2, boolean... skippaa) {
        for (int i = 0; i < koordinaatti.length; i++) {
            if(skippaa.length > i){
                if(skippaa[i]){
                    continue;
                }
            }
            double k = koordinaatti[i];
            double k1Temp = 0;
            if (koordinaatti1.koordinaatteja() > i) {
                k1Temp = koordinaatti1.koordinaatti[i] - k;
            }
            double k2Temp = 0;
            if (koordinaatti2.koordinaatteja() > i) {
                k2Temp = koordinaatti2.koordinaatti[i] - k;
            }
            if (k1Temp < 0 ^ k2Temp < 0) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return Arrays.toString(koordinaatti);
    }

    private int koordinaatteja() {
        return koordinaatti.length;
    }
}
