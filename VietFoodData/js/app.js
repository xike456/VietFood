// Initialize Firebase

var app = angular.module('VietFoodApp', ["firebase"]);
app.controller('VietFoodController', function ($scope, $firebaseObject, $firebaseArray, $compile) {

	var ref2 = firebase.database().ref();
	// download the data into a local object
	$scope.data = $firebaseObject(ref2);

	var ref = firebase.database().ref("recipes/all");
	// download the data into a local object
	$scope.recipes = $firebaseArray(ref);
	console.log($scope.recipes);

	//Default Values
	$scope.divHome = false;
	$scope.divShowData = false;
	$scope.divAddData = true;
	$scope.divUpload = false;

	$scope.txt_id = '';
	$scope.txt_recipeName = '';
	$scope.txt_category = '';
	$scope.txt_demoImage = '';
	$scope.txt_difficulty = '';

	$scope.txt_Giavi = [];
	$scope.txt_ingredients = [];
	$scope.txt_ingredients2 = [];

	$scope.txt_review = '';
	$scope.txt_step = [];
	$scope.txt_step2 = [];

	$scope.txt_time = '';

	$scope.CountStep = 0;
	$scope.CountGiavi = 0;

	$scope.showHome = function () {
		$scope.hideAll();
		$scope.divHome = true;
	};
	//methods for showing/hiding divs
	$scope.showAddData = function () {
		$scope.hideAll();
		$scope.divAddData = true;
	};
	$scope.showData = function () {
		$scope.hideAll();
		$scope.divShowData = true;
	};
	//methods for showing/hiding divs
	$scope.showUpload = function () {
		$scope.hideAll();
		$scope.divUpload = true;
	};
		//hide all divs
	$scope.hideAll = function () {
		$scope.divShowData = false;
		$scope.divHome = false;
		$scope.divAddData = false;
		$scope.divUpload = false;

	};

	$scope.AddGiaVi = function () {
		$scope.CountGiavi++;
		var fTenGV = angular.element( document.querySelector('#frmTenGiaVi'));
		var fSoLuongGV = angular.element( document.querySelector('#frmSoLuongGiaVi'));

		var inputTenGV = angular.element('<input type="text" id="idTenGV'+ $scope.CountGiavi +'" class="form-control" ng-model="txt_ingredients['+ $scope.CountGiavi +']" placeholder="Tên gia vị">');
		var inputSoLuongGV = angular.element('<input type="text" id="idSoLuongGV'+ $scope.CountGiavi +'" class="form-control" ng-model="txt_ingredients2['+ $scope.CountGiavi +']" placeholder="Số lượng" >');

		var compile = $compile(inputTenGV)($scope);
		compile = $compile(inputSoLuongGV)($scope);

		fTenGV.append(inputTenGV);
		fSoLuongGV.append(inputSoLuongGV);
		};

	$scope.DeleteGiaVi = function () {
		if ($scope.CountGiavi==0) return;
		var fTenGV = angular.element( document.querySelector('#idTenGV'+ $scope.CountGiavi +''));
		var fSoLuongGV = angular.element( document.querySelector('#idSoLuongGV'+ $scope.CountGiavi +''));

		fTenGV.remove();
		fSoLuongGV.remove();
		$scope.CountGiavi--;
	};

	$scope.AddStep = function () {
		$scope.CountStep++;
		var fStep = angular.element( document.querySelector('#frmStep'));
		var input = angular.element('<textarea type="text" id="idStep'+ $scope.CountStep +'" class="form-control" ng-model="txt_step['+ $scope.CountStep +']" placeholder="Bước '+ ($scope.CountStep + 1) +'" ></textarea>');
		var compile = $compile(input)($scope);
		fStep.append(input);
	};

	$scope.DeleteStep = function () {
		if ($scope.CountStep==0) return;
		var fStep = angular.element( document.querySelector('#idStep'+ $scope.CountStep +''));
		fStep.remove();
		$scope.CountStep--;
	};


	$scope.AddRecipe = function () {

		// A post entry.
		for(var i = 0; i < $scope.txt_ingredients.length;i++){
			{
				var temp = {
					name : $scope.txt_ingredients[i],
					amount : $scope.txt_ingredients2[i],
				}

				$scope.txt_Giavi.push(temp);
			}
		}

		for(var i = 0; i < $scope.txt_step.length;i++){
			{
				var temp2 = {
					step: $scope.txt_step[i],
				}
				$scope.txt_step2.push(temp2);
			}
		}

		firebase.database().ref('recipes/all/'+$scope.txt_id).set({
			recipeName: $scope.txt_recipeName,
			category: $scope.txt_category,
			demoImage: $scope.txt_demoImage,
			difficulty: $scope.txt_difficulty,
			ingredients: $scope.txt_Giavi,
			review: $scope.txt_review,
			steps: $scope.txt_step2,
			time: $scope.txt_time
		});
		console.log($scope.txt_step);
		console.log($scope.txt_ingredients);


	}

});