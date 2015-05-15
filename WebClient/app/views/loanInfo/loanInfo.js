'use strict';
var baseUrl = 'http://localhost:8190';

angular.module('myApp.loanInfo', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/loan', {
            templateUrl: 'views/loanInfo/loanInfo.html',
            controller: 'LoanInfoCtrl'
        });
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
        $httpProvider.defaults.transformResponse.push(function(responseData){
            convertDateStringsToDates(responseData);
            return responseData;
        });
    })
    .factory('Loan',function($resource) {
        return $resource(baseUrl + '/api/loans/:id', {}, {
            editInfo: {method: "PUT", url: baseUrl + '/api/loans/:id'},
            addInfo: {method: "POST", url: baseUrl + '/api/loans/byClient/:id'},
            addClient: {method: "POST", url: baseUrl + '/api/client'},
            loanByClient: {method: "POST", url: baseUrl + '/api/loans/byClient'},
            isExists: {method: "POST", url: baseUrl + '/api/loans/isExists'},
            getBanks: {method: "GET", url: baseUrl + '/api/banks', isArray: false},
            getCurrency: {method: "GET", url: baseUrl + '/api/currency', isArray: false},
            newBanks: {method: "POST", url: baseUrl + '/api/banks'},
            newCurrency: {method: "POST", url: baseUrl + '/api/currency'}
        });
    })
    .controller('LoanInfoCtrl', function ($scope,$modal, Loan) {

        var self = $scope;

        self.searchKeyword = null;
        self.loans = null;

        self.getAllRecords = function(){
            Loan.get().$promise.then(
                function(data) {
                    self.loans = data.clients;
                },
                function(errors) {
                    self.errors=errors;
                }
            );
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

angular.module('myApp.loanInfo').controller('EditModalInstanceCtrl', function ($scope, $modalInstance, Loan, item) {
    var self = $scope;
    self.item = angular.copy(item);

    self.ok = function () {
        var newItem={

        };
        var loanId = item.id;
        var clientId = item.person.id;
        Loan.editInfo({
            id:clientId
        },newItem).$promise.then(
            function(data) {
                console.log(data);
                self.loans=data;
            },
            function(data) {
                self.errors=data;
                console.log(data);
            }
        );
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.loanInfo').controller('NewClientModalInstanceCtrl', function ($scope, $modalInstance, $http) {

    $scope.item=null;

    $scope.ok = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});
angular.module('myApp.loanInfo').controller('NewInfoModalInstanceCtrl', function ($scope, $modalInstance, Loan, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        var newItem = item;
        console.log(newItem);
        var clientId = item.person.id;
        Loan.addInfo({
            id:clientId
        },newItem).$promise.then(
            function(data) {
                self.loans=data;
                console.log(data);
            },
            function(data) {
                self.errors=data;
                console.log(data);
            }
        );
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
