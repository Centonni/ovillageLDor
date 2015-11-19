'use strict';

angular.module('ovillageLDorApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


