package com.example.shiro;

import com.example.shiro.mapper.UserMapper;
import com.example.shiro.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		User user=userMapper.getByUserName("admin");
		System.out.println(user);
	}

}
