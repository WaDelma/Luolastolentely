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
    public Koordinaatti(double... koordinaatti) {
        this.koordinaatti = koordinaatti.clone();
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
     * @param koordinaatti
     */
    public void muuta(Koordinaatti koordinaatti) {
        muuta(koordinaatti.koordinaatti);
    }

    /**
     *
     * @param koordinaatti
     */
    public void muuta(double[] koordinaatti) {
        if (this.koordinaatti == null || koordinaatti.length >= this.koordinaatti.length) {
            this.koordinaatti = koordinaatti.clone();
            return;
        }
        System.arraycopy(koordinaatti, 0, this.koordinaatti, 0, koordinaatti.length);
    }

    /**
     *
     * @param koordinaatti
     */
    public void siirra(Koordinaatti koordinaatti) {
        siirra(koordinaatti.koordinaatti);
    }

    /**
     *
     * @param koordinaatti
     */
    public void siirra(double... koordinaatti) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new double[koordinaatti.length];
        }
        if (koordinaatti.length > this.koordinaatti.length) {
            double[] temp = new double[koordinaatti.length];
            System.arraycopy(this.koordinaatti, 0, temp, 0, this.koordinaatti.length);
            this.koordinaatti = temp;
        }
        for (int n = 0; n < koordinaatti.length; n++) {
            this.koordinaatti[n] += koordinaatti[n];
        }
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys koordinaatista
     */
    public double etaisyys(Koordinaatti koordinaatti) {
        return etaisyys(koordinaatti.koordinaatti);
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys toiseeen koordinaatista
     */
    public double etaisyysToiseen(Koordinaatti koordinaatti) {
        return etaisyysToiseen(koordinaatti.koordinaatti);
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys koordinaatti listasta
     */
    public double etaisyys(double[] koordinaatti) {
        return Math.sqrt(etaisyysToiseen(koordinaatti));
    }

    /**
     *
     * @param koordinaatti
     * @return Etäisyys toiseeen koordinaatti listasta
     */
    public double etaisyysToiseen(double[] koordinaatti) {
        if (this.koordinaatti == null) {
            this.koordinaatti = new double[koordinaatti.length];
        }
        if (koordinaatti.length > this.koordinaatti.length) {
            System.arraycopy(this.koordinaatti, 0, new double[koordinaatti.length], 0, this.koordinaatti.length);
        }
        double result = 0;
        for (int n = 0; n < koordinaatti.length; n++) {
            double temp = koordinaatti[n] - this.koordinaatti[n];
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
