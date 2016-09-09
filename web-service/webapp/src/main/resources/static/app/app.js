// Define the `phonecatApp` module
var cromApp = angular.module('cromApp', ['ngRoute']);

cromApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'static/view/watch.html',
            controller: 'WatchListing'
        })
        .when('/project/:projectName', {
            templateUrl: 'static/view/project-listing.html',
            controller: 'ProjectListing'
        })
        .when('/project/:projectName/repo/:repoName', {
            templateUrl: 'static/view/repo-listing.html',
            controller: 'RepoListing'
        })
        .when('/project/:projectName/repo/:repoName/version', {
            templateUrl: 'static/view/version-listing.html',
            controller: 'VersionListing'
        })
        .otherwise({
            redirectTo: "/"
        });
});

cromApp.controller('VersionListing', function ($scope, $http, $routeParams) {
    var projectName = $routeParams['projectName'];
    var repoName = $routeParams['repoName'];
    $scope.projectName = projectName;
    $scope.repoName = repoName;
    $scope.displayJson = false;

    $http.get('/api/v1/project/' + projectName + '/repo/' + repoName + '/versions').then(function (response) {
        $scope.versionDetails = response.data;
    });
});

cromApp.controller('RepoListing', function ($scope, $http, $routeParams) {
    var projectName = $routeParams['projectName'];
    var repoName = $routeParams['repoName'];
    $scope.projectName = projectName;
    $scope.repoName = repoName;
    $scope.displayJson = false;
    $scope.linkLess = function (input) {
        var result = {};
        for (var i in input) {
            if(i != 'links') {
                result[i] = input[i];
            }
        }
        return result;
    };

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
