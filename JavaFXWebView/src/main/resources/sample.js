// sample business logic, for demonstration purposes kept in one file

var app = angular.module('sampleApp', ['ngRoute']);

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/about', {
                    templateUrl: 'view-about.html',
                    activeTab: 'about'
                }).
                when('/home', {
                    templateUrl: 'view-home.html',
                    activeTab: 'home'
                }).
                when('/contracts', {
                    templateUrl: 'view-contracts.html',
                    activeTab: 'contracts'
                }).
                when('/invoices', {
                    templateUrl: 'view-invoices.html',
                    activeTab: 'invoices'
                }).
                otherwise({
                    redirectTo: '/home'
                });
    }]);

app.run(['$rootScope', '$route', function ($rootScope, $route) {
        $rootScope.isTabActive = function (tabName) {
            if (!$route.current) {
                return false;
            }
            return $route.current.activeTab === tabName;
        };
    }]);

app.factory('InvoiceService', function () {
    
    var api =  {
        
        getInvoices: function () {
            
            var invoices = [];
            
            var invoice = {number: "acme-2", date: "2015-11-01", companyId: 1, paid: false }; 
            
            invoices.push(invoice);
            
            invoice = {number: "acme-1", date: "2015-10-21", companyId: 1, paid: true }; 
            invoices.push(invoice);
            
            invoice = {number: "acme-1", date: "2015-10-21", companyId: 1, paid: true }; 
            invoices.push(invoice);
            
            invoice = {number: "wonder-1", date: "2015-11-11", companyId: 2, paid: false }; 
            invoices.push(invoice);
            
            return invoices;
        }
    };
    
    if (window.appFrame) {
        
        api.getInvoices = function () {
            var data =  window.appFrame.getInvoices();
            
            return JSON.parse(data);
        };
    }
    
    return api;

});

app.factory('ContractService', function ($http) {
    
    return {
        
        getContracts: function () {
            
            return $http.get("/api/contracts");
        }
    };
});


app.factory('MailService', function ($http) {
    
    var api = {
        sendMail: function (contract) {

            $("#mailReceiver").text(contract.name + ", " + contract.company);
            $("#mailDialog").modal('show');
        }
    };
    
    if (window.appFrame) {
        
        api.sendMail = function (contract) {
            window.appFrame.sendMail(contract);
        };
    }
    
    return api;

});



app.controller("ContractCtrl", function($scope, MailService, ContractService) {
    ContractService.getContracts().then(function(result) {
        $scope.contracts = result.data;
    });
    
    $scope.sendMail = function (contract) {
        MailService.sendMail(contract);
    };
    
});

app.controller("InvoiceCtrl", function($scope, InvoiceService, ContractService) {
    
    ContractService.getContracts().then(function(result) {
        $scope.contracts = result.data;
    });
    
    $scope.findCompanyById = function (id) {
        
        if (!$scope.contracts) {
            return {};
        }
        
        for(var i = 0; i < $scope.contracts.length; i++) {
            var item = $scope.contracts[i];
            if (item.companyId == id) {
                return item;
            }
        }
        return {};
    };
    
    $scope.invoices = InvoiceService.getInvoices();
    
    
});
