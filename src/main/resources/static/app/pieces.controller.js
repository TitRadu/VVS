(function(){
    'use strict'

    angular
        .module('app')
        .controller('PiecesController', PiecesController );

    PiecesController.$inject = ['$http'];

    function PiecesController($http){
        var vm = this;

        vm.pieces = [];
        vm.getAll = getAll;
        vm.getAffordable = getAffordable;
        vm.deletePiece = deletePiece;
        vm.addPiece = addPiece;


        init();

        function init(){
            getAll();

        }

        function getAll(){
            var url="all";
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function getAffordable(){
            var price;
            price = document.getElementById("affordablePrice").value;
            var url="lessThan/" + price;
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function deletePiece(id){
            var url="delete/"+id;
            $http.post(url).then(function(response){
                vm.pieces = response.data;

            });

        }

        function addPiece(){
            var url="save";
            $http.post(url).then(function(response){
                vm.pieces = response.data;

            });

        }

    }

})();