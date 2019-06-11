//控制层
app.controller('gdjbController', function ($scope) {

    $scope.helloWorld = "你好!世界!";
        $scope.helloWorld = function () {
            $scope.helloWorld = "世界!你好!";

         /*   gdjbService.findAll().success(

            function (response) {
                    $scope.projectFindAll();

                }
            );*/
        };

    }
);
