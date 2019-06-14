package com.gdjb.oauth.controller;

import com.gdjb.oauth.pojo.oauth.ThirdClient;
import com.gdjb.oauth.server.ThirdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @类名 : ThirdClient
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/14 10:14
 * @版本 1.0
 */

@RestController
@RequestMapping("/thirdClient")
public class ThirdClientController {


    @Autowired
    private ThirdClientService thirdClientService;

    @GetMapping("/findAll")
    public List findAll() {
        List<ThirdClient> all = thirdClientService.findAll();
        return all;
    }

    @PostMapping("/add")
    public boolean add(@RequestBody ThirdClient thirdClient) {
        int add = thirdClientService.add(thirdClient);
        return add != 0;
    }


    @GetMapping("/findById")
    public ThirdClient findById(String clientId) {
        ThirdClient byId = thirdClientService.findById(clientId);
        return byId;
    }


    @PostMapping("/updata")
    public boolean updata(@RequestBody ThirdClient thirdClient) {
        int updata = thirdClientService.updata(thirdClient);
        return updata != 0;
    }

    @PostMapping("/delete")
    public boolean delete(String clientId) {
        int delete = thirdClientService.delete(clientId);
        return delete != 0;
    }
}
    