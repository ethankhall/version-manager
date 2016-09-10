// Define the `phonecatApp` module
var cromApp = angular.module('cromApp', ['ngRoute']);

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

    if($scope.selectedVersion != null) {
        $scope.view($scope.selectedVersion);
    }

    $http.get('/api/v1/project/' + projectName + '/repo/' + repoName + '/versions').then(function (response) {
        $scope.versionDetails = response.data;
    });
});

cromApp.controller('RepoListing', function ($scope, $http, $routeParams, JsonFilterService) {
    var projectName = $routeParams['projectName'];
    var repoName = $routeParams['repoName'];
    $scope.projectName = projectName;
    $scope.repoName = repoName;
    $scope.displayJson = false;
    $scope.linkLess = JsonFilterService.linkLess;

    $http.get('/api/v1/project/' + projectName + '/repo/' + repoName).then(function (response) {
        $scope.repoDetails = response.data;
    });
});

cromApp.controller('ProjectListing', function ($scope, $http, $routeParams) {
    var projectName = $routeParams['projectName'];
    $scope.projectName = projectName;
    $scope.displayJson = false;
    $http.get('/api/v1/project/' + projectName).then(function (response) {
        $scope.projectDetails = response.data;
    });
});

cromApp.controller('WatchListing', function ($scope, $http) {
    $http.get('/api/v1/user/watches').then(function (response) {
        $scope.watches = response.data.watches;
    });
});
