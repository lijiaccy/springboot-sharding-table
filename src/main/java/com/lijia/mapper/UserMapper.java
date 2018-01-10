package com.lijia.mapper;

import com.lijia.bean.User;
import org.apache.ibatis.annotations.*;

import java.sql.JDBCType;

@Mapper
public interface UserMapper {

    @Select("select * from user where id =#{id}")
    public User getUser(@Param("id") Long id);

    @Insert("insert into user(id,name,sex) values(#{id},#{name},#{sex})")
    void create(User user);

    @Select("select * from user order by id desc limit 1")
    User getUserMax();

    @Select("select logLevel from logs where logClass='123'")
    String getOthers();
}
