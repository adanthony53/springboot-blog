package com.example.service;

import com.example.dao.UserRepository;
import com.example.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userRepository.findByUsernameAndPassword(username, encodedPassword);
        return user;
    }
}
