'use strict';

angular.module('myApp.recommendations', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/recommendations', {
    templateUrl: 'views/recommendations/recommendations.html',
    controller: 'RecommendationsCtrl'
  });
}])

.controller('RecommendationsCtrl', function($scope,$http) {
      var scope = $scope;
      scope.answer = null;

      scope.getAnswer = function () {
        return $scope.answer
      };


      scope.getGoogle = function() {
        $http.get('https://www.google.com.ua/search?q=ang&ie=utf-8&oe=utf-8&gws_rd=cr&ei=6UkgVbWXA8f2O9PfgPgB')
            .success(function (data) {
              scope.answer = data;
            });
      };
});