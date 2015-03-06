package com.privat.bki.entities;

import java.io.Serializable;

/**
 * Created by sting on 1/24/15.
 */
public interface IDirectory extends Serializable {

    public String getCode();
    public void setCode(String code);
    public String getName();
    public void setName(String name);
}
