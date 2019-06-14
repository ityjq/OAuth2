package com.gdjb.oauth.dao;

import com.gdjb.oauth.pojo.oauth.ThirdClient;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * @类名 : ThirdClient
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/14 9:58
 * @版本 1.0
 */

@Mapper
@Repository
public interface ThirdClientDao {

    @Select("select * from oauth_client_details")
    @Results(id="ThirdClient", value={
            @Result(property = "clientId", column = "client_id"),
            @Result(property = "resourceIds", column = "resource_ids"),
            @Result(property = "redirectUris", column = "web_server_redirect_uri"),
            @Result(property = "scopes", column = "scope"),
            @Result(property = "authorizedGrantTypes", column = "authorized_grant_types"),
            @Result(property = "secret", column = "client_secret")})
    ArrayList<ThirdClient> findAll();

    @Select("select * from oauth_client_details WHERE client_id = #{clientId}")
    @ResultMap("ThirdClient")
    ThirdClient findById(String clientId);

    @Insert("INSERT INTO oauth_client_details (`client_id`,`client_secret`,`resource_ids`,`web_server_redirect_uri`,`scope`,`authorized_grant_types`) VALUES (#{clientId},#{secret},#{resourceIds},#{redirectUris},#{scopes},#{authorizedGrantTypes})")
    int add(ThirdClient thirdClient);


    @Update("update oauth_client_details set client_secret=#{secret}, resource_ids=#{resourceIds}, web_server_redirect_uri=#{redirectUris}, scope=#{scopes}, authorized_grant_types=#{authorizedGrantTypes}  where client_id =#{clientId}")
    int updata(ThirdClient thirdClient);


    @Delete("DELETE FROM oauth_client_details WHERE `client_id` = #{clientId}")
    int delete(String clientId);

}