package aopkarja.kasittely;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Kasittelija, jonka {@link KasittelynHoitaja} käsittelee.
 * 
 * @author aopkarja
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface Kasittelija {

    KasittelyTyyppi value();
}
