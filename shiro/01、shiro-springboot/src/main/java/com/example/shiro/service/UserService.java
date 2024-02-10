package com.example.shiro.service;

import com.example.shiro.pojo.User;

import java.util.List;

public interface UserService {
    public User getByUserName(String name);

    public String getUserRole(int id);

    public List<String> getRolePerms(int id);


}
