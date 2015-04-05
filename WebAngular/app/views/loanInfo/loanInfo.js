'use strict';

angular.module('myApp.loanInfo', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/loanInfo', {
            templateUrl: 'views/loanInfo/loanInfo.html',
            controller: 'LoanInfoCtrl'
        });
    }])

    .controller('LoanInfoCtrl', function ($scope,$modal) {
        var self = $scope;

        self.query = null;

        var
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
        }

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
                templateUrl: 'views/editModal/editModal.html',
                controller: 'ModalInstanceCtrl',
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
    });

angular.module('myApp.loanInfo').controller('ModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.item = angular.copy(item);

    $scope.ok = function () {
        $modalInstance.close($scope.item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});