'use strict';

angular.module('myApp.login', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'views/login/login.html',
            controller: 'LoginCtrl'
        });
        $httpProvider.defaults.transformResponse.push(function(responseData){
            convertDateStringsToDates(responseData);
            return responseData;
        });
    })
    .controller('LoginCtrl', function($rootScope, $scope, $http, $location) {

        var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
            + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('http://localhost:8181/login', {headers : headers}).success(function(data) {
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

        }

        authenticate();
        $scope.credentials = {};
        $scope.login = function() {
            authenticate($scope.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    $scope.error = false;
                } else {
                    $location.path("/login");
                    $scope.error = true;
                }
            });
        };
    });