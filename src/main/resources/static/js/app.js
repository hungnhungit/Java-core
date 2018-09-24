var app = angular.module("RoleManagement", []);

// Controller

app.controller("RoleController", function($scope, $http) {
	
	 $scope.roles = [];
	 $scope.roleForm = {
		id : null,
		name : ""
	 };
	 $scope.search = ""
     $scope.sortType = 'id'
     $scope.sortReverse  = true
	 
	 var url = "/v1/api/roles/create";
     var method = "POST";
	 _refreshRoleData();
	 
	function _refreshRoleData() {
        $http({
            method: 'GET',
            url: '/v1/api/roles'
        }).then(
            function(res) { // success
                $scope.roles = res.data;
                console.log(res.data);
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
	
	 $scope.submitEmployee = function() {
	        $http({
	            method: method,
	            url: url,
	            data: angular.toJson($scope.roleForm),
	            headers: {
	                'Content-Type': 'application/json'
	            }
	        }).then(res => {
	        	_refreshRoleData();
	        	_clearForm();
	        });
	    };
	    
	    $scope.roleEdit = function(role){
	    	url = "/v1/api/roles/update"
	    	method = "PATCH"
	    	$scope.roleForm.name = role.name;
	    	$scope.roleForm.id = role.id;
	    }
	    
	    $scope.roleDelete = function(role){
	    	 $http({
	             method: 'DELETE',
	             url: '/v1/api/roles/delete/'+role.id
	         }).then(_succes)};
                  
	    var _succes = () => {
	    	_refreshRoleData();
	    }
	    var _clearForm = () => {
	    	$scope.roleForm = {
	    		name : ""
	    	}
	    }
	
});