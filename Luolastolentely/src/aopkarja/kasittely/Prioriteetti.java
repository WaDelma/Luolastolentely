package aopkarja.kasittely;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Kertoo {@link Kasittelija}n prioriteetin.
 * Isompi numero korkeampi prioriteetti.
 * 
 * @author aopkarja
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface Prioriteetti {

    int value() default 0;
}
