<div class="generic-container">
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="lead">User </span>
		</div>
		<div class="panel-body">
			<div class="formcontainer">
				<div class="alert alert-success" role="alert" ng-if="data.successMessage">{{data.successMessage}}
				</div>
				<div class="alert alert-danger" role="alert" ng-if="data.errorMessage">{{data.errorMessage}}
				</div>
				<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
					<fieldset ng-disabled="data.buttonRunDisable">
						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.operador.$dirty && myForm.operador.$error.required}">
								<label class="col-md-2 control-lable" for="operador">Cantidad de
									operadores
								</label>
								<div class="col-md-7">
									<input type="text" name="operador" ng-model="data.properties.operadores"
										id="operador" class="form-control input-sm" required
										ng-keypress="filterValue($event)" />
								</div>
								<span
									ng-show="myForm.operador.$dirty && myForm.operador.$error.required">campo requerido</span>
							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.sup.$dirty && (myForm.sup.$error.required || myForm.sup.$error.pattern)}">
								<label class="col-md-2 control-lable" for="sup">Cantidad de
									supervisores
								</label>
								<div class="col-md-7">
									<input type="text" name="sup" ng-model="data.properties.supervisores"
										id="age" class="form-control input-sm" required ng-keypress="filterValue($event)" />
								</div>
								<span ng-show="myForm.sup.$dirty && myForm.sup.$error.required">campo requerido</span>
								<span ng-show="myForm.sup.$error.pattern">campo numerico</span>
							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.jef.$dirty && (myForm.jef.$error.required || myForm.jef.$error.pattern)}">
								<label class="col-md-2 control-lable" for="jef">Cantidad de
									Jefes
								</label>
								<div class="col-md-7">
									<input type="text" name="jef" ng-model="data.properties.jefes"
										id="salary" class="form-control input-sm" required
										ng-keypress="filterValue($event)" />
								</div>
								<span ng-show="myForm.jef.$dirty && myForm.jef.$error.required">campo requerido</span>
							</div>
						</div>

						<div class="row">
							<div class="form-group col-md-12"
								ng-class="{'has-error': myForm.llamada.$dirty && myForm.llamada.$error.required}">
								<label class="col-md-2 control-lable" for="llamada">Cantidad de
									llamadas a procesar
								</label>
								<div class="col-md-7">
									<input type="text" name="llamada" ng-model="data.properties.llamadas"
										id="llamada" class="form-control input-sm" required
										ng-keypress="filterValue($event)" />
								</div>
								<span ng-show="myForm.llamada.$dirty && myForm.llamada.$error.required">campo requerido</span>
							</div>
						</div>


						<div class="row">
							<div class="form-actions floatRight">
								<input type="button" ng-click="submit()" value="Disparar Llamadas"
									class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">
			<span class="lead">List of Users </span>
		</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID LLAMDA</th>
							<th>DURACION LLAMDA</th>
							<th>OPERADOR</th>
							<th>ROL</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="d in data.registroLlamada">
							<td>{{d.id}}</td>
							<td>{{d.duracion}}</td>
							<td>{{d.operador.nombre}}</td>
							<td>{{d.operador.puesto}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>