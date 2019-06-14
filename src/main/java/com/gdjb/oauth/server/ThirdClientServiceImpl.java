package com.gdjb.oauth.server;

import com.gdjb.oauth.dao.ThirdClientDao;
import com.gdjb.oauth.pojo.oauth.ThirdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名 : ThirdClient
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/14 10:13
 * @版本 1.0
 */

@Service
@Transactional
public class ThirdClientServiceImpl implements ThirdClientService {

    @Autowired
    private ThirdClientDao thirdClientDao;


    @Override
    public List<ThirdClient> findAll() {
        ArrayList<ThirdClient> all = thirdClientDao.findAll();
     //   System.out.println("aaa");
        return all;
    }

    @Override
    public int add(ThirdClient thirdClient) {
        return thirdClientDao.add(thirdClient);
    }

    @Override
    public ThirdClient findById(String clientId) {
        return thirdClientDao.findById(clientId);
    }

    @Override
    public int updata(ThirdClient thirdClient) {
        return thirdClientDao.updata(thirdClient);
    }


    @Override
    public int delete(String clientId) {
        return thirdClientDao.delete(clientId);
    }
}
   