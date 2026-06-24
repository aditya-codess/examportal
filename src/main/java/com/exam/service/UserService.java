package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    public User createUser(User user, Set<UserRole> userRoles);

    User getUser(String username);

    //delete user
    public void  deleteUser(Long userId);
}
