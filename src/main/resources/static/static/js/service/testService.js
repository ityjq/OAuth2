//服务层
app.service('testService', function ($http) {

    //读取列表数据绑定到表单中
    this.findAll = function () {
        //return $http.get('../');
         return $http.get('https://v1.hitokoto.cn/');
    }
 //   http://localhost:8080/oauth/authorize?client_id=jie&response_type=code&redirect_uri=http://localhost:8090/
    this.gainCode = function () {
         return $http.get ('../gainCode');
    }
    this.gainToken = function (code) {
        // return $http.get('../gainToken?code='+code)
         return $http.post('http://jie:jbkj@localhost:8080/oauth/token?redirect_uri='+'http://localhost:8090/&grant_type=authorization_code&code='+code);
    }

});
