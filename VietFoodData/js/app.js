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
	$scope.divShowData = true;
	$scope.divAddData = false;
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

	$scope.CountStep = 1;
	$scope.CountGiavi = 1;

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
		var fGiaVi = angular.element( document.querySelector('#frmGiaVi'));
		var input = angular.element('<label>=================</label><input type="text" class="form-control" ng-model="txt_ingredients['+ $scope.CountGiavi+']" placeholder="Nhập gia vị món ăn"><input type="text" class="form-control" ng-model="txt_ingredients2['+ $scope.CountGiavi+']" placeholder="Nhập số lượng gia vị">');
		var compile = $compile(input)($scope);
		fGiaVi.append(input);
		$scope.CountGiavi++;
		};

	$scope.AddStep = function () {
		var fStep = angular.element( document.querySelector('#frmStep'));
		var input = angular.element('<input type="text" class="form-control" ng-model="txt_step['+ $scope.CountStep +']" placeholder="Nhập các bước nấu món ăn" >');
		var compile = $compile(input)($scope);
		fStep.append(input);
		$scope.CountStep++;
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