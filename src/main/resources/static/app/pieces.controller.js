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
            var price = document.getElementById("affordablePriceInput").value;
            var priceWarning = document.getElementById("priceWarning");
            var regex = /^[0-9]+\.?[0-9]*$/i;

            if(!price.replace(/\s/g, '').length){
                priceWarning.innerHTML = "Please introduce a price!";
                return false;
            }else{
                priceWarning.innerHTML = null;

            }

            if(price <= 0){
                priceWarning.innerHTML = "Please introduce a price higher than 0!";
                return false;

            }else{
                priceWarning.innerHTML = null;

            }

            if(!regex.test(price)){
                priceWarning.innerHTML = "Please introduce a valid price!";
                return false;

            }else{
                priceWarning.innerHTML = null;

            }

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