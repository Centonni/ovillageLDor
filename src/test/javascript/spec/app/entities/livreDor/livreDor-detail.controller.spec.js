'use strict';

describe('LivreDor Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockLivreDor, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockLivreDor = jasmine.createSpy('MockLivreDor');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'LivreDor': MockLivreDor,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("LivreDorDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'ovillageLDorApp:livreDorUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
