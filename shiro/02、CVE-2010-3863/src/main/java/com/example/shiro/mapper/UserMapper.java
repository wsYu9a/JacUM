package com.example.shiro.mapper;

import com.example.shiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    public User getByUserName(String name);

    public String getUserRole(int id);

    public List<String> getRolePerms(int id);

}
