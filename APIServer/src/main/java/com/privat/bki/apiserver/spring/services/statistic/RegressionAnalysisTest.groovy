package com.privat.bki.apiserver.spring.services.statistic

import spock.lang.Specification


class RegressionAnalysisTest extends Specification {

    def "CalculatePrognosticationForBank"() {
        setup:
        RegressionAnalysis ra = new RegressionAnalysis()
        ra.regressionModel = Mock(LinearRegressionModel)
        def preparedValues = [privat: [
                [year:2011, count:213],
                [year:2012, count:234],
                [year:2013, count:212],
                [year:2010, count:246],
                [year:2009, count:251]
        ]]
        def bankName = 'privat'
        int prognosYear = 2015

        ra.regressionModel.evaluateAt(_) >> 0

        expect:
        ra.calculatePrognosticationForBank(preparedValues,bankName,prognosYear) == 0
    }
}
