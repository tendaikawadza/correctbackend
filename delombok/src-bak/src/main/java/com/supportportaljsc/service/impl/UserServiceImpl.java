package com.supportportaljsc.service.impl;



import com.supportportaljsc.domain.User;
import com.supportportaljsc.domain.UserPrincipal;
import com.supportportaljsc.repository.UserRepository;
import com.supportportaljsc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.supportportaljsc.enumeration.Role.ROLE_USER;

@Component
@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
@Autowired

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            LOGGER.error("NO_USER_FOUND_BY_USERNAME " + username);

            throw new UsernameNotFoundException("User not found" + username);


        } else {

            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info("FOUND_USER_BY_USERNAME" + username);
            return userPrincipal;
        }

    }

}

















































