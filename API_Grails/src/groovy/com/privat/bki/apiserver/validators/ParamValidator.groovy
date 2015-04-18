package com.privat.bki.apiserver.validators

/**
 * Created by sting on 3/29/15.
 */
class ParamValidator {
    def errors = []
    def paramsShouldExist = [:]
    def params = [:]

    ParamValidator(Map paramsShouldExist, Map params) {
        this.paramsShouldExist = paramsShouldExist
        this.params = params
        validateParams(paramsShouldExist,params)
    }

    private Boolean validateParams(Map paramsShouldExist, Map params) {
        for (param in paramsShouldExist) {
            if (!params.containsKey(param)) {
                errors.add("Required parameter not specified.")
            }
        }
    }

    boolean isValid() {
        return errors?.size() <= 0
    }
}
