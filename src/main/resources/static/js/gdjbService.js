//服务层
app.service('gdjbService', function ($http) {

    //查询未绑定的所有数据
    this.findAll = function () {
        return $http.get('../control/equipment/findAllNull');
    }
});
