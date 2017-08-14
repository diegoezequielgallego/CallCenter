angular.module('crudApp').controller('ConfigController', ['ConfigService', '$scope',  
	function( service, $scope) {
		
		//se crean todas las variables y objetos dentro de 
		//otro asi a la hora de limpiarlos es mas optimo angular
		$scope.data = {
			config: {},
			properties: {},
			configList: [],
			registroLlamada: [],
			successMessage : '',
			errorMessage : '',
			buttonRunDisable : false
		}

        
        $scope.filterValue = function($event){
        	var keys = {
        	        'up': 38, 'right': 39, 'down': 40, 'left': 37,
        	        'escape': 27, 'backspace': 8, 'tab': 9, 'enter': 13, 'del': 46,
        	        '0': 48, '1': 49, '2': 50, '3': 51, '4': 52, '5': 53, '6': 54, '7': 55, '8': 56, '9': 57, 'dash':189, 'subtract':109
        	    };
        	    for (var index in keys) {
        	        if (!keys.hasOwnProperty(index)) continue;
        	        if (event.charCode == keys[index] || event.keyCode == keys[index]) {
        	            return; //default event
        	        }
        	    }
        	    event.preventDefault();
        };
        
        $scope.submit = function() {
            console.log('Submitting');
            $scope.data.buttonRunDisable = true;
            $scope.data.successMessage = 'Corriendo ...';
            $scope.data.configList = []; 
            
            
            //se crea una lista de obj config que indica el tipo de operador y la cantidad
            
            $scope.data.config = {tipoOperador: 1 , cantidad: $scope.data.properties.operadores , descripcion: "operador"};
            $scope.data.configList.push($scope.data.config);
            
            $scope.data.config = {tipoOperador: 2 , cantidad: $scope.data.properties.supervisores , descripcion: "supervisor"};
            $scope.data.configList.push($scope.data.config);
            
            $scope.data.config = {tipoOperador: 3 , cantidad: $scope.data.properties.jefes , descripcion: "director"};
            $scope.data.configList.push($scope.data.config);
            
            service.runConfig($scope.data.configList, $scope.data.properties.llamadas)
            .then(
                function (response) {
                	$scope.data.successMessage = 'la nueva configuracion corrió exitosamente';
                	$scope.data.errorMessage='';
                    $scope.loadLog();
                },
                function (errResponse) {
                	data.errorMessage = 'Error while run config: ' + errResponse.data.errorMessage;
                	data.successMessage='';
                	$scope.data.buttonRunDisable = false;
                }
            );
           
        }
        
        
        $scope.loadLog = function() {
            service.loadLog().then(
                function (registroLlamada) {
                	$scope.data.registroLlamada = registroLlamada;
                	$scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
                    $scope.data.buttonRunDisable = false;
                }
            );
            
        }
        
        
    }


    ]);