'use strict';

angular.module('myApp.logining', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/logining', {
            templateUrl: 'views/logining/logining.html',
            controller: 'LoginingCtrl'
        });
        $httpProvider.defaults.transformResponse.push(function(responseData){
            convertDateStringsToDates(responseData);
            return responseData;
        });
    })
    .controller('LoginingCtrl', function ($scope,$modal, $http) {

    });