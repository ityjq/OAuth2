//控制层
app.controller('testController', function ($scope, $timeout, $http, testService) {

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
            testService.gainCode($scope.code).success(
                function (response) {
                    $scope.code = response;
                    alert($scope.code);
                }
            );
        };

        $scope.code = "";
        $scope.gainToken = function () {
            // window.clearInterval(t2);

            if ($scope.code == null)
                alert($scope.code)
            else {
                alert("进来了:" + $scope.code)
                testService.gainToken($scope.code).success(
                    /*function (response) {
                     //   $scope.token = response;
                       // alert($scope.token.access_token);
                    }*/
                );
            }
        };
        $scope.token = ""
        $scope.test1 = function () {
            // window.clearInterval(t2);

            if ($scope.code == null)
                alert($scope.code)
            else {
                testService.test1($scope.code).success(
                    function (response) {
                        $scope.coder = response;
                        alert($scope.coder);
                    }
                );
            }
        };
        $scope.redirect1 = function () {
            // window.clearInterval(t2);

            if ($scope.code == null)
                alert($scope.code)
            else {
                $http.jsonp('http://localhost:8080/oauth/authorize?client_id=jie&response_type=code&redirect_uri=http://localhost:8090/').success(function (lastData, status, headers) {
                    alert(status, status, headers)
                    if (status == 302) {
                        alert(status, status, headers)

                    } else if (status == 200) {
                        alert(200);
                    }
                });


            }
        };


    }
);
