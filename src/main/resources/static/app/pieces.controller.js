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


        init();

        function init(){
            getAll();

        }

        function getAll(){
            var url="Pieces/all";
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function getAffordable(){
            var url="/Pieces/maiIeftinDecat/" + 500;
            var piecesPromise = $http.get(url);
            piecesPromise.then(function(response){
                vm.pieces = response.data

            });

        }

        function deletePiece(id){
            var url="/Pieces/delete/"+id;
            $http.post(url).then(function(response){
                vm.pieces = response.data;

            });

        }

    }

})();