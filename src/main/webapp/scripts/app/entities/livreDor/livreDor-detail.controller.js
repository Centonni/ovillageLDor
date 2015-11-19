'use strict';

angular.module('ovillageLDorApp')
    .controller('LivreDorDetailController', function ($scope, $rootScope, $stateParams, entity, LivreDor, User) {
        $scope.livreDor = entity;
        $scope.load = function (id) {
            LivreDor.get({id: id}, function(result) {
                $scope.livreDor = result;
            });
        };
        var unsubscribe = $rootScope.$on('ovillageLDorApp:livreDorUpdate', function(event, result) {
            $scope.livreDor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
