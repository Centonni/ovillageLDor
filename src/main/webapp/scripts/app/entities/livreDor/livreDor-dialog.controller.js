'use strict';

angular.module('ovillageLDorApp').controller('LivreDorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'LivreDor', 'User',
        function($scope, $stateParams, $modalInstance, entity, LivreDor, User) {

        $scope.livreDor = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            LivreDor.get({id : id}, function(result) {
                $scope.livreDor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ovillageLDorApp:livreDorUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.livreDor.id != null) {
                LivreDor.update($scope.livreDor, onSaveSuccess, onSaveError);
            } else {
                LivreDor.save($scope.livreDor, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
