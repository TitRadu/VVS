(function(){
    'use strict'

    angular
        .module('app')
        .controller('PiecesController', PiecesController );

    PiecesController.$inject = ['$http'];

    function PiecesController($http){
        var vm = this;

        vm.pieces = [];
        vm.listAllPieces = listAllPieces;
        vm.listFilterPieces = listFilterPieces;
        vm.removePiece = removePiece;


        init();

        function init(){
            listAllPieces();

        }

        function listAllPieces(){
            var url="all";
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function listFilterPieces(){
            var price;
            price = document.getElementById("affordablePrice").value;
            var url="lessThan/" + price;
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function removePiece(id){
            var url="remove/"+id;
            $http.delete(url).then(function(response){
                vm.pieces = response.data;

            });

        }

    }

})();