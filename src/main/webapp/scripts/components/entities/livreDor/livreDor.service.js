'use strict';

angular.module('ovillageLDorApp')
    .factory('LivreDor', function ($resource, DateUtils) {
        return $resource('api/livreDors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateVisite = DateUtils.convertLocaleDateFromServer(data.dateVisite);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dateVisite = DateUtils.convertLocaleDateToServer(data.dateVisite);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dateVisite = DateUtils.convertLocaleDateToServer(data.dateVisite);
                    return angular.toJson(data);
                }
            }
        });
    });
