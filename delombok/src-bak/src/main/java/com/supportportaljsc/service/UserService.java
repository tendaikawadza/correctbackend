package com.supportportaljsc.service;

import com.supportportaljsc.domain.User;
import com.supportportaljsc.exception.domain.EmailExistException;
import com.supportportaljsc.exception.domain.UserNotFoundException;
import com.supportportaljsc.exception.domain.UsernameExistException;

import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException,
            UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);



}
