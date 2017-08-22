app.factory('ShrimpFarmService', ['$http', function($http) {
	return {
		get: function(id) {
			return $http.get('/feedEntry/' + id);
		},
 
		create: function(petData) {
			return $http.post('/feedEntry', petData);
		},
 
		delete: function(id) {
			return $http.delete('/pets/' + id);
		},
		
		update: function(petData) {
			return $http.put('/pets/' + petData.id, petData);
		}
	}
}]);

app.factory('Mapping', ['$http', function($http) {
	return {
		get: function() {
			return $http.get('/mappings');
		}
	}
}]);