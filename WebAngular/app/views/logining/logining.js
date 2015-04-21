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

        var self = $scope;

        self.query = null;
        self.loans = null;

        $scope.getAllRecords = function(){

            $http({method: 'GET', url: 'http://localhost:8181/login'}).
                success(function(data) {
                    $scope.loans = angular.fromJson(data.clients);
                }).
                error(function(data) {
                    $scope.errors=data;
                });
        };

        self.selectedItem = null;

        self.selectItem = function(item) {
            if (!item) {
                alert('Please, select an item');
                return;
            }
            self.selectedItem = item;
        };

        self.openEditModal = function(item){
            if (!item) {
                alert('Please, select an item');
                return;
            }

            var modalInstance = $modal.open({
                templateUrl: 'views/modals/editModal.html',
                controller: 'EditModalInstanceCtrl',
                resolve: {
                    item: function () {
                        return item;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                var selectedItemIndex = $scope.loans.indexOf($scope.selectedItem);
                $scope.loans[selectedItemIndex] = angular.copy(item);
            });
        };

        self.openNewClientModal = function(){

            var modalInstance = $modal.open({
                templateUrl: 'views/modals/newClientModal.html',
                controller: 'NewClientModalInstanceCtrl'

            });

            modalInstance.result.then(function (item) {
                var selectedItemIndex = $scope.loans.indexOf($scope.selectedItem);
                $scope.loans[selectedItemIndex] = angular.copy(item);
            });
        };
        self.openNewInfoModal = function(item){
            if (!item) {
                alert('Please, select an item');
                return;
            }

            var modalInstance = $modal.open({
                templateUrl: 'views/modals/newInfoModal.html',
                controller: 'NewInfoModalInstanceCtrl',
                resolve: {
                    item: function () {
                        return item;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                var selectedItemIndex = $scope.loans.indexOf($scope.selectedItem);
                $scope.loans[selectedItemIndex] = angular.copy(item);
            });
        }

    })
    .directive('stRatio',function(){
        return {
            link:function(scope, element, attr){
                var ratio=+(attr.stRatio);

                element.css('width',ratio+'%');

            }
        };
    });

angular.module('myApp.logining').controller('EditModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.logining').controller('NewClientModalInstanceCtrl', function ($scope, $modalInstance) {

    $scope.item=null;

    $scope.ok = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.logining').controller('NewInfoModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
