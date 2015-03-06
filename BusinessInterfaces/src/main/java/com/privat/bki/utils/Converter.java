package com.privat.bki.utils;

/**
 * Created by sting on 2/1/15.
 */
public interface Converter<T, T1> {
    /**
     * перевод из T в T1
     * @param date
     * @return
     */
    T1 from(T date);
    /**
     * перевод из T1 в T
     * @param date
     * @return
     */
    T to(T1 ld);

    Class<T> fromType();

    Class<T1> toType();
}
