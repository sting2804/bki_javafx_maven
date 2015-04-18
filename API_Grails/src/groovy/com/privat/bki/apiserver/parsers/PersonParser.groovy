package com.privat.bki.apiserver.parsers

import com.privat.bki.entities.Person

/**
 * Created by sting on 3/29/15.
 */
class PersonParser implements AbstractParser{
    @Override
    def parseObject(Map params){
        return new Person(params.id, params.name, params.surname, params.patronymic,
                params.birthday, params.inn, params.passNumber, params.passSerial)
    }
}
