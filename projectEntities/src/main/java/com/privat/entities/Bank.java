package com.privat.entities;

/**
 * Created by sting on 1/24/15.
 */
public class Bank implements IDirectory{

    private String code;
    private String name;

    public Bank() {
    }

    public Bank(String code, String name) {
        this.code=code;
        this.name=name;
    }

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public void setCode(String bankCode) {
        this.code = bankCode;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String bankName) {
        this.name = bankName;
    }
}
