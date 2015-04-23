package com.privat.bki.business.entities
/**
 * Created by sting on 1/24/15.
 */
interface IDirectory extends Serializable {

    String getCode()
    void setCode(String code)
    String getName()
    void setName(String name)
}
