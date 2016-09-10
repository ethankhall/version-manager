// Define the `phonecatApp` module
var cromApp = angular.module('cromApp', ['ngCookies', 'ngRoute']);

cromApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'static/view/watch.html'
        })
        .when('/project/:projectName', {
            templateUrl: 'static/view/project-listing.html'
        })
        .when('/project/:projectName/repo/:repoName', {
            templateUrl: 'static/view/repo-listing.html'
        })
        .when('/project/:projectName/repo/:repoName/version/:version?', {
            templateUrl: 'static/view/version-listing.html'
        })
        .otherwise({
            redirectTo: "/"
        });
});

cromApp.factory('WatchService', function ($http) {
    return {
        watch: function (watch) {
            var urlPost = watch.projectName;
            if (null != watch.repoName) {
                urlPost += '/repo/' + watch.repoName;
            }
            $http.post('/api/v1/user/watch/project/' + urlPost);
        }
    };
});

cromApp.factory('JsonFilterService', function () {
    return {
        linkLess: function (input) {
            var result = {};
            for (var i in input) {
                if (i != 'links') {
                    result[i] = input[i];
                }
            }
            return result;
        }
    };
});

cromApp.factory('LoginService', function ($cookies) {
    var authToken = null;
    if ($cookies.get('crom_cookie') != null) {
        authToken = $cookies.get('crom_cookie')
    }

    return {
        auth: function () {
            return authToken;
        }
    }
});

cromApp.controller('LoginController', function ($scope, $cookies, LoginService) {
    $scope.authToken = LoginService.auth();
});


cromApp.controller('VersionListing', function ($scope, $http, $routeParams, JsonFilterService) {
    var projectName = $routeParams['projectName'];
    var repoName = $routeParams['repoName'];
    $scope.selectedVersion = $routeParams['version'];
    $scope.projectName = projectName;
    $scope.repoName = repoName;
    $scope.displayJson = false;
    $scope.linkLess = JsonFilterService.linkLess;

    $scope.view = function (id) {
        $http.get('/api/v1/project/' + projectName + '/repo/' + repoName + '/version/' + id).then(function (response) {
            $scope.selectedVersion = id;
            $scope.versionJson = response.data;
        });
    };

    if ($scope.selectedVersion != null) {
        $scope.view($scope.selectedVersion);
    }

    $http.get('/api/v1/project/' + projectName + '/repo/' + repoName + '/versions').then(function (response) {
        $scope.versionDetails = response.data;
    });
});

cromApp.controller('RepoListing', function ($scope, $http, $routeParams, LoginService, JsonFilterService, WatchService) {
    var projectName = $routeParams['projectName'];
    var repoName = $routeParams['repoName'];
    $scope.authToken = LoginService.auth();
    $scope.projectName = projectName;
    $scope.repoName = repoName;
    $scope.displayJson = false;
    $scope.linkLess = JsonFilterService.linkLess;
    $scope.watch = function (projectName, repoName) {
        WatchService.watch({'projectName': projectName, 'repoName': repoName});
    };

    $http.get('/api/v1/project/' + projectName + '/repo/' + repoName).then(function (response) {
        $scope.repoDetails = response.data;
    });
});

cromApp.controller('ProjectListing', function ($scope, $http, $routeParams, LoginService, WatchService) {
    var projectName = $routeParams['projectName'];

    $scope.authToken = LoginService.auth();

    $scope.projectName = projectName;
    $scope.displayJson = false;

    $scope.watch = function (projectName) {
        WatchService.watch({'projectName': projectName});
    };

    $http.get('/api/v1/project/' + projectName).then(function (response) {
        $scope.projectDetails = response.data;
    });
});

cromApp.controller('WatchListing', function ($scope, $http) {
    $http.get('/api/v1/user/watches').then(function (response) {
        $scope.watches = response.data.watches;
    });
});
