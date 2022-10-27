package com.supportportaljsc.service.impl;

import com.supportportaljsc.domain.User;
import com.supportportaljsc.domain.UserPrincipal;
import com.supportportaljsc.exception.domain.EmailExistException;
import com.supportportaljsc.exception.domain.UserNotFoundException;
import com.supportportaljsc.exception.domain.UsernameExistException;
import com.supportportaljsc.repository.UserRepository;
import com.supportportaljsc.resource.LoginAttemptService;
import com.supportportaljsc.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;


import static com.supportportaljsc.enumeration.Role.ROLE_USER;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service
@Transactional
@Qualifier("userDetailsService")

public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;

    private LoginAttemptService loginAttemptService;


    public UserServiceImpl(Logger LOGGER, UserRepository userRepository, LoginAttemptService loginAttemptService) {
        this.LOGGER = LOGGER;
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error("NO_USER_FOUND_BY_USERNAME " +username);
            throw new UsernameNotFoundException("NO_USER_FOUND_BY_USERNAME "+ username);}

        else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info("FOUND_USER_BY_USERNAME" + username);

            return userPrincipal;
        }




    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }


    }


    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}










