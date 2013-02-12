package aopkarja.kasittely;

/**
 * Tapahtuma, jonka {@link KasittelynHoitaja} antaa {@link Kasittelija}lle, jos
 * {@link Kasittelija} voi sen vastaanottaa.
 *
 * @author Antti
 */
public abstract class Tapahtuma {
    
    private Class<?>[] tallennettu;
    private Tapahtuma tyyppi;
    
    public Tapahtuma(Tapahtuma tyyppi){
        this.tyyppi = tyyppi;
    }
    
    public Tapahtuma(){
    }

    /*
     *
     */
    public abstract Object[] getTieto();
    
    public Class<?>[] getRakenne(){
        if(tallennettu == null){
            tallennettu = new Class<?>[getTieto().length];
            for (int i = 0; i < tallennettu.length; i++) {
                tallennettu[i] = getTieto()[i].getClass();
            }
        }
        return tallennettu.clone();
    }
    
    public Tapahtuma getTyyppi(){
        return tyyppi;
    }
}
