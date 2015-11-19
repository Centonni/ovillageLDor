'use strict';

angular.module('ovillageLDorApp')
	.controller('LivreDorDeleteController', function($scope, $modalInstance, entity, LivreDor) {

        $scope.livreDor = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LivreDor.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });