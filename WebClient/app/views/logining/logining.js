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
    .controller('LoginingCtrl', function($rootScope, $scope, $http, $location) {

            var authenticate = function(credentials, callback) {

                var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('user', {headers : headers}).success(function(data) {
                    if (data.name) {
                        $rootScope.authenticated = true;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback && callback();
                }).error(function() {
                    $rootScope.authenticated = false;
                    callback && callback();
                });
    }});