package com.example.shiro.service.Impl;

import com.example.shiro.mapper.UserMapper;
import com.example.shiro.pojo.User;
import com.example.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getByUserName(String name) {
        User user = userMapper.getByUserName(name);
        return user;
    }

    @Override
    public String getUserRole(int id) {
        String role=userMapper.getUserRole(id);
        return role;
    }

    @Override
    public List<String> getRolePerms(int id) {
        List<String> perms = userMapper.getRolePerms(id);
        return perms;
    }


}
