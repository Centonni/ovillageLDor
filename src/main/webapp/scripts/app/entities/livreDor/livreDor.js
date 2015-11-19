'use strict';

angular.module('ovillageLDorApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('livreDor', {
                parent: 'entity',
                url: '/livreDors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ovillageLDorApp.livreDor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/livreDor/livreDors.html',
                        controller: 'LivreDorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('livreDor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('livreDor.detail', {
                parent: 'entity',
                url: '/livreDor/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ovillageLDorApp.livreDor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/livreDor/livreDor-detail.html',
                        controller: 'LivreDorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('livreDor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LivreDor', function($stateParams, LivreDor) {
                        return LivreDor.get({id : $stateParams.id});
                    }]
                }
            })
            .state('livreDor.new', {
                parent: 'livreDor',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livreDor/livreDor-dialog.html',
                        controller: 'LivreDorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    message: null,
                                    nomVisiteur: null,
                                    prenomVisiteur: null,
                                    contact: null,
                                    twitter: null,
                                    mail: null,
                                    dateVisite: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('livreDor', null, { reload: true });
                    }, function() {
                        $state.go('livreDor');
                    })
                }]
            })
            .state('livreDor.edit', {
                parent: 'livreDor',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livreDor/livreDor-dialog.html',
                        controller: 'LivreDorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LivreDor', function(LivreDor) {
                                return LivreDor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('livreDor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('livreDor.delete', {
                parent: 'livreDor',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livreDor/livreDor-delete-dialog.html',
                        controller: 'LivreDorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LivreDor', function(LivreDor) {
                                return LivreDor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('livreDor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
