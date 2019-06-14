package com.gdjb.oauth.server;

import com.gdjb.oauth.pojo.oauth.ThirdClient;

import java.util.List;

/**
 * @类名 : ThirdClient
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/14 10:12
 * @版本 1.0
 */
public interface ThirdClientService {
    /**
     * 查询所有ThirdClient
     *
     * @return
     */
    List<ThirdClient> findAll();

    /**
     * 增加
     *
     * @param thirdClient
     * @return
     */
    int add(ThirdClient thirdClient);

    /**
     * 根据id查询
     *
     * @param clientId
     * @return
     */
    ThirdClient findById(String clientId);

    /**
     * 根据id修改
     *
     * @param thirdClient
     * @return
     */
    int updata(ThirdClient thirdClient);

    /**
     * 根据id删除
     *
     * @param clientId
     * @return
     */
    int delete(String clientId);
}
