package com.gdjb.oauth.dao;

import com.gdjb.oauth.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @ClassName : User
 * @Description : TODO
 * @Author : gile
 * @Date : 2019/5/16 19:06
 * @Version 1.0
 */

@Mapper
@Repository
public interface UserDao {

    @Select("select * from user")
    ArrayList<User> findAll();

    @Select("select * from user WHERE `id` = #{id} ")
    User findById(Integer id);

    @Insert("INSERT INTO `control`.`user` (`username`, `password`, `roles`) VALUES (#{username},#{password},#{roles})")
//    @Insert("INSERT INTO user ('username','password','roles') VALUES (#{username},#{password},#{roles})")
    int add(User user);


    @Update("update user set username=#{username}, password=#{password} ,roles=#{roles} where id =#{id}")
    int updata(User user);


    @Delete("DELETE FROM user WHERE `id` = #{id}")
    int delete(Integer id);

    @Select("select * from user WHERE username = #{username}")
    User findUserByUsername(String username);
}