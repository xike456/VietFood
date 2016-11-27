// Initialize Firebase


var app = angular.module('VietFoodApp', ["firebase"]);
app.controller('VietFoodController', function ($scope, $firebaseObject, $firebaseArray, $compile) {

	var ref = firebase.database().ref("recipes/all");
	// download the data into a local object
	$scope.recipes = $firebaseArray(ref);
	console.log($scope.recipes);
	$scope.existID = false;



	//Default Values
	$scope.divHome = false;
	$scope.divShowData = false;
	$scope.divAddData = true;
	$scope.divUpload = false;

	$scope.txt_id = 0;
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
    $scope.txt_stepImg = [];
    $scope.txt_stepImg2 = [];

    $scope.txt_time = '';
    $scope.txt_view = '';

	$scope.CountStep = 0;
	$scope.CountGiavi = 0;

	$scope.resetAll = function () {
        window.location.reload();
    }

	$scope.showHome = function () {
		$scope.hideAll();
		$scope.divHome = false;
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
		var fPreGiavi = angular.element( document.querySelector('#frmPreGiavi'));

		var inputTenGV = angular.element('<input type="text" id="idTenGV'+ $scope.CountGiavi +'" class="form-control" ng-model="txt_ingredients['+ $scope.CountGiavi +']" placeholder="Tên gia vị" required>');
		var inputSoLuongGV = angular.element('<input type="text" id="idSoLuongGV'+ $scope.CountGiavi +'" class="form-control" ng-model="txt_ingredients2['+ $scope.CountGiavi +']" placeholder="Số lượng" required>');
		var inputPreGiavi = angular.element('<li id="idPreGiaVi'+ $scope.CountGiavi +'"><span ng-bind="txt_ingredients['+ $scope.CountGiavi +']"></span>: <span ng-bind="txt_ingredients2['+ $scope.CountGiavi +']"></span><br/></li>');

		var compile = $compile(inputTenGV)($scope);
		compile = $compile(inputSoLuongGV)($scope);
		compile = $compile(inputPreGiavi)($scope);
		fTenGV.append(inputTenGV);
		fSoLuongGV.append(inputSoLuongGV);
        fPreGiavi.append(inputPreGiavi);

        toastr.success('Thêm gia vị thành công!');
    };

	$scope.DeleteGiaVi = function () {
		if ($scope.CountGiavi==0) return;
		var fTenGV = angular.element( document.querySelector('#idTenGV'+ $scope.CountGiavi +''));
		var fSoLuongGV = angular.element( document.querySelector('#idSoLuongGV'+ $scope.CountGiavi +''));
        var fPreGiavi = angular.element( document.querySelector('#idPreGiaVi'+ $scope.CountGiavi +''));

		fTenGV.remove();
		fSoLuongGV.remove();
		fPreGiavi.remove();

        //Preview
        var fPreGiaviTen = angular.element( document.querySelector('#idPreGiaViTen'+ $scope.CountGiavi +''));
        var fPreGiaviSoLuong = angular.element( document.querySelector('#idPreGiaViSoLuong'+ $scope.CountGiavi +''));
        fPreGiaviTen.remove();
        fPreGiaviSoLuong.remove();

        $scope.CountGiavi--;
        toastr.warning('Xóa gia vị thành công!');
	};

	$scope.AddStep = function () {
		$scope.CountStep++;
		var fStep = angular.element( document.querySelector('#frmStep'));
        var fStep2 = angular.element( document.querySelector('#frmPreStep'));

        var input = angular.element('<label id="idStepLabel'+ $scope.CountStep +'">- Bước '+ ($scope.CountStep + 1) +'</label><textarea type="text" id="idStep'+ $scope.CountStep +'" class="form-control" ng-model="txt_step['+ $scope.CountStep +']" placeholder="Bước '+ ($scope.CountStep + 1) +'" required></textarea> <input type="text" id="idStepImg'+ $scope.CountStep +'" class="form-control" ng-model="txt_stepImg['+ $scope.CountStep +']" placeholder="Link ảnh bước '+ ($scope.CountStep + 1) +'" required >');
		var input2 = angular.element('<li id="idPreStep'+ $scope.CountStep +'"><img ng-src="{{txt_stepImg['+ $scope.CountStep +']}}" alt="Ảnh lỗi!" height="auto" width="auto"><br/><span ng-bind="txt_step['+ $scope.CountStep +']"></span><br/></li>');
		var compile = $compile(input)($scope);
         compile = $compile(input2)($scope);

        fStep.append(input);
        fStep2.append(input2);
        toastr.success('Thêm bước thực hiện thành công!');

    };

	$scope.DeleteStep = function () {
		if ($scope.CountStep==0) return;
        var fStepLabel = angular.element( document.querySelector('#idStepLabel'+ $scope.CountStep +''));
        var fStep = angular.element( document.querySelector('#idStep'+ $scope.CountStep +''));
        var fStepImg = angular.element( document.querySelector('#idStepImg'+ $scope.CountStep +''));
        var fPreStep = angular.element( document.querySelector('#idPreStep'+ $scope.CountStep +''));

		fStep.remove();
        fStepImg.remove();
        fStepLabel.remove();
        fPreStep.remove();
		$scope.CountStep--;
        toastr.warning('Xóa bước thực hiện thành công!');
    };


    $scope.AutoGetRecipeID = function () {
        var tempID=0;
        $scope.checkRecipeID(tempID);
        while ($scope.existID != false) {
            tempID++;
            $scope.checkRecipeID(tempID);
			if (tempID > 2000) return;
        };
        $scope.txt_id = tempID;
        toastr.success('Get ID thành công. ID có thể dùng là:'+tempID);
    }



	$scope.AddRecipe = function () {

        $scope.checkRecipeID($scope.txt_id);
        if ($scope.existID==true) {
            //alert("ID đã tồn tại. Vui lòng chọn lại!");
            toastr.error('ID đã tồn tại, vui lòng chọn lại.');
            return;
        };

		// A post entry.
		for(var i = 0; i <= $scope.CountGiavi;i++){
			{
				var temp = {
					name : $scope.txt_ingredients[i],
					amount : $scope.txt_ingredients2[i],
				}

				$scope.txt_Giavi.push(temp);
			}
		}

		for(var i = 0; i <= $scope.CountStep;i++){
			{
				var temp2 = {
                    image: $scope.txt_stepImg[i],
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
			time: $scope.txt_time,
			view: $scope.txt_view
		});

        toastr.success('THÊM CÔNG THỨC THÀNH CÔNG!');
    };

    $scope.checkRecipeID = function (recipeID) {
        var refID = firebase.database().ref('recipes/all/' + recipeID );
        refID.once('value', function(snapshot) {
            $scope.existID =(snapshot.val() !== null);
        });

    };

	$scope.checkID = function(recipeID){
        $scope.checkRecipeID(recipeID);
        if ($scope.existID==true) {
          //  alert("ID đã tồn tại. Vui lòng chọn lại!");
            toastr.error('ID đã tồn tại, vui lòng chọn lại.');
        }
        else
		{
            toastr.success('ID có thể sử dụng.');
		}
    };

 	// $scope.UploadCopyLink = function () {
    //     ZeroClipboard.config( { swfPath: "https://cdnjs.cloudflare.com/ajax/libs/zeroclipboard/2.2.0/ZeroClipboard.swf" } );
    //     var client = new ZeroClipboard($("#btnCopy"));
    //
    //     client.on("copy", function (event) {
    //         var copiedValue = $('#txtTestInput').val();
    //         var clipboard = event.clipboardData;
    //         clipboard.setData("text/plain", copiedValue);
    //         alert('The copied value is: ' + copiedValue);
    //     });
    // }

	$scope.uploadFile = function(){

		// Create a root reference
		var storageRef = firebase.storage().ref();



		var selectedFile = $('#input')[0].files[0];

		var metadata = {
			contentType: 'image/jpeg'
		};

		var uploadTask = storageRef.child('images/' + selectedFile.name).put(selectedFile, metadata);

		uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
			function(snapshot) {
				var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                toastr.warning('Upload is ' + progress.toFixed(0) + '% done');
                $(".progress-bar").animate({
                    width: progress.toFixed(0) + '%'
                }, 2500);
                //console.log('Upload is ' + progress + '% done');
				switch (snapshot.state) {
					case firebase.storage.TaskState.PAUSED: // or 'paused'
                        toastr.success('Upload is paused');
                        //console.log('Upload is paused');
						break;
					case firebase.storage.TaskState.RUNNING: // or 'running'
                        //toastr.success('Upload is running');
                        console.log('Upload is running');
						break;
				}
			}, function(error) {
				switch (error.code) {
					case 'storage/unauthorized':
						break;

					case 'storage/canceled':
						break;

					case 'storage/unknown':
						// Unknown error occurred, inspect error.serverResponse
						break;
				}
			}, function() {
                toastr.success('Upload is Completed!');

                var downloadURL = uploadTask.snapshot.downloadURL;
				console.log(downloadURL);
				var fpreview = angular.element( document.querySelector('#previewImg'));
				var input = angular.element('<img src="'+ downloadURL +'" width="150" height="150">');
				var compile = $compile(input)($scope);
				fpreview.append(input);
			});
	};
});