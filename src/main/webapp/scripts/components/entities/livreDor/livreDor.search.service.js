'use strict';

angular.module('ovillageLDorApp')
    .factory('LivreDorSearch', function ($resource) {
        return $resource('api/_search/livreDors/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
