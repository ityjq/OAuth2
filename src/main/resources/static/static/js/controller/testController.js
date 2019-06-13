//控制层
com.gdjb.oauth.controller('testController', function ($scope, $timeout, testService) {

        $scope.aaa = function () {
            // window.clearInterval(t2);
            testService.findAll().success(
                function (response) {
                    $scope.hitokoto = response;
                    $timeout(function () {
                        $scope.aaa();
                    }, 5000);
                    // $scope.addHitokoto($scope.hitokoto);
                    // alert($scope.testlist);
                }
            );
        };


        $scope.gainCode = function () {
            // window.clearInterval(t2);
            testService.gainCode().success(
                function (response) {
                    $scope.code = response;
                    alert($scope.code);
                }
            );
        };

        $scope.code = "";
        $scope.gainToken = function () {
            // window.clearInterval(t2);

            if ($scope.code==null)
                alert($scope.code)
            else {
                alert( "进来了:"+ $scope.code)
                testService.gainToken($scope.code).success(
                    function (response) {
                        $scope.token = response;
                        alert($scope.token.access_token);
                    }
                );
            }
        };

    }
);
