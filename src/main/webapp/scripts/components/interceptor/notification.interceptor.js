 'use strict';

angular.module('ovillageLDorApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-ovillageLDorApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-ovillageLDorApp-params')});
                }
                return response;
            }
        };
    });
