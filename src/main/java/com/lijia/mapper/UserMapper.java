package com.lijia.mapper;

import com.lijia.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where id =#{id}")
    public User getUser(@Param("id") Long id);

    @Insert("insert into user(id,name,sex) values(#{id},#{name},#{sex})")
    void create(User user);

    @Select("select * from user order by id desc limit 1")
    User getUserMax();
}
