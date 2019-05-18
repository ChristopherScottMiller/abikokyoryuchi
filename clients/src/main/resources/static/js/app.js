"use strict";

// Define your client-side logic here.


const app = angular.module('myAppModule', ['ui.bootstrap']);

app.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRefections(false);
}]);

app.controller('MyAppController', function ($http, $location, uibModal) {
    const myApp = this;

    const apiBaseURL = "api/warranty/";
    let peers = [];

    $http.get(apiBaseURL + "me").then((response) => myApp.thisNode = response.data.me);


});
