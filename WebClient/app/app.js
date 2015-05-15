'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngResource',
    'myApp.about',
    'myApp.home',
    'myApp.loanInfo',
    'myApp.logining',
    'myApp.recommendations',
    'ui.bootstrap',
    'smart-table'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/home',
            controller: 'IndexCtrl'
        });
    }])
    .controller('IndexCtrl', function ($scope, $location) {
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };
    });
