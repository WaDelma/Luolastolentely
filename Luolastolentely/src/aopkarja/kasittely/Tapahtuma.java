package aopkarja.kasittely;

/**
 * Tapahtuma, jonka {@link KasittelynHoitaja} antaa {@link Kasittelija}lle, jos
 * {@link Kasittelija} voi sen vastaanottaa.
 *
 * @author Antti
 */
public interface Tapahtuma {

    /*
     * Tiedon rakenteen löydät toteuttavista luokista.
     */
    public Object[] getTieto();
}
