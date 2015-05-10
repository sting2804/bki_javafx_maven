'use strict';

angular.module('myApp.loanInfo', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/loanInfo', {
            templateUrl: 'views/loanInfo/loanInfo.html',
            controller: 'LoanInfoCtrl'
        });
        $httpProvider.defaults.transformResponse.push(function(responseData){
            convertDateStringsToDates(responseData);
            return responseData;
        });
    })
    .controller('LoanInfoCtrl', function ($scope,$modal, $http) {

        var self = $scope;

        self.query = null;
        self.loans = null;

        $scope.getAllRecords = function(){

            $http({method: 'GET', url: 'http://localhost:8181/select/all.json'}).
                success(function(data) {
                    $scope.loans = angular.fromJson(data.clients);
                }).
                error(function(data) {
                    $scope.errors=data;
                });
        };

        /*var
            nameList = ['Pierre', 'Pol', 'Jacques', 'Robert', 'Elisa'],
            familyList = ['Dupont', 'Germain', 'Delcourt', 'bjip', 'Menez'],
            patronymicList = ['Alex', 'Ovish', 'ololo'],
            passSerialList = ['AN', 'AE', 'AL', 'AQ', 'OO'],
            currencyList = ['UAH', 'USD', 'RUB'];

        function createRandomItem() {
            var
                firstName = nameList[Math.floor(Math.random() * 4)],
                lastName = familyList[Math.floor(Math.random() * 4)],
                patronymic = patronymicList[Math.floor(Math.random() * 2)],
                currency = currencyList[Math.floor(Math.random() * 2)],
                passSerial = passSerialList[Math.floor(Math.random() * 4)],
                birthday = Math.floor(Math.random() * 2010)+'-'+Math.floor(Math.random()*11)+'-'+Math.floor(Math.random()*29),
                opened = Math.floor(Math.random() * 2010)+'-'+Math.floor(Math.random()*11)+'-'+Math.floor(Math.random()*29),
                closed = Math.floor(Math.random() * 2010)+'-'+Math.floor(Math.random()*11)+'-'+Math.floor(Math.random()*29),
                passNumber = Math.floor(Math.random() * 999999),
                balance = Math.random() * 4000,
                amount = Math.random() * 5000;

            return{
                name: firstName,
                surname: lastName,
                patronymic: patronymic,
                birthday: birthday,
                inn: '3322131',
                passSerial: passSerial,
                passNumber: passNumber,
                amount: amount,
                balance: balance,
                currency: currency,
                bank: 'adfsd',
                opened: opened,
                closed: closed,
                arrears: 'true'
            };
        }

        self.loans = [];
        for (var j = 0; j < 500; j++) {
            $scope.loans.push(createRandomItem());
        }*/

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

angular.module('myApp.loanInfo').controller('EditModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.loanInfo').controller('NewClientModalInstanceCtrl', function ($scope, $modalInstance) {

    $scope.item=null;

    $scope.ok = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.loanInfo').controller('NewInfoModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});

var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;

function convertDateStringsToDates(input) {
    // Ignore things that aren't objects.
    if (typeof input !== "object") return input;

    for (var key in input) {
        if (!input.hasOwnProperty(key)) continue;

        var value = input[key];
        var match;
        // Check for string properties which look like dates.
        if (typeof value === "string" && (match = value.match(regexIso8601))) {
            var milliseconds = Date.parse(match[0])
            if (!isNaN(milliseconds)) {
                input[key] = new Date(milliseconds);
            }
        } else if (typeof value === "object") {
            // Recurse into object
            convertDateStringsToDates(value);
        }
    }
}
