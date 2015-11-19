'use strict';

angular.module('ovillageLDorApp')
    .controller('LivreDorController', function ($scope, $state, $modal, LivreDor, LivreDorSearch) {
      
        $scope.livreDors = [];
        $scope.loadAll = function() {
            LivreDor.query(function(result) {
               $scope.livreDors = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LivreDorSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.livreDors = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.livreDor = {
                message: null,
                nomVisiteur: null,
                prenomVisiteur: null,
                contact: null,
                twitter: null,
                mail: null,
                dateVisite: null,
                id: null
            };
        };
    });
