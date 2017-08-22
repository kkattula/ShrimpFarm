app.controller('ShrimpFarmController', ['$scope', 'ShrimpFarmService','Mapping', function ShrimpFarmController($scope, Pets,Mapping) {
 
	$scope.formData = {};
 
    // when landing on the page, get all Pets and show them
	Pets.get()
    .success(function(data) {
        $scope.pets = data;
    });
 
    // when submitting the add form, send the text to the spring API
    $scope.createPet = function() {
        if(!$scope.petsForm.$valid) {
            return;
        }
        Pets.create($scope.formData)
        .success(function(data) {
            $scope.formData = {}; // clear the form so our user is ready to
									// enter another
            $scope.pets.push(data);
        });
    };
 
    // delete a todo after checking it
    $scope.deletePet = function(id) {
        Pets.delete(id)
        .success(function(data) {
        	angular.forEach($scope.pets, function(pet, index){
                if(pet.id == id) {
                	$scope.pets.splice(index, 1);
                }
        	});
        });
    };
    
    // when submitting the add form, send the text to the node API
    $scope.savePet = function(pet) {
        Pets.update(pet)
        .success(function(data) {
            $scope.editedPet = {};
        });
    };
 
    $scope.editedPet = {};
 
    $scope.editPet = function(pet) {
        $scope.editedPet = pet;
    }
 
    $scope.revertPet = function() {
        $scope.editedPet = {};
    }
 
}]);

