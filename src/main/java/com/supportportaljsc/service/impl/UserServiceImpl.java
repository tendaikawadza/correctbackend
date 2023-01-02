package com.supportportaljsc.service.impl;



import com.supportportaljsc.domain.User;
import com.supportportaljsc.domain.UserPrincipal;
import com.supportportaljsc.enumeration.Role;
import com.supportportaljsc.exception.domain.*;
import com.supportportaljsc.repository.UserRepository;
import com.supportportaljsc.service.EmailService;
import com.supportportaljsc.service.LoginAttemptService;
import com.supportportaljsc.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.persistence.AssociationOverride;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.supportportaljsc.constant.FileCostant.DEFAULT_USER_IMAGE_PATH;
import static com.supportportaljsc.constant.FileCostant.USER_FOLDER;
import static com.supportportaljsc.enumeration.Role.ROLE_USER;
import static org.apache.logging.log4j.util.Strings.EMPTY;


@Component
@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;

    private EmailService emailService;
      @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }






    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);

        if (user != null) {
            LOGGER.error("NO_USER_FOUND_BY_USERNAME " + username);

            throw new UsernameNotFoundException("User not found" + username);


        } else {

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
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {

        validateNewUsernameAndEmail(StringUtils.EMPTY,username,email);
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        //user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(firstName, password, email);
        return user;


    }


    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        String password = generatePassword();
        user.setUserId(generateUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setJoinDate(new Date());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepository.save(user);
        saveProfileImage(user, profileImage);
        LOGGER.info("New user password: " + password);
        return user;
    }

    private void saveProfileImage(User user, MultipartFile profileImage) {
    }

    private String getTemporaryProfileImageUrl(String username) {

        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();


    }

    private Role getRoleEnumName(String role) {


        return Role.valueOf(role.toUpperCase());



    }

//    private String getTemporaryProfileImageUrl(String username) {
//
//    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp");
//
//
//
//    }

    private String encodePassword(String password) {

    return  passwordEncoder.encode(password);
    }

    private String generatePassword() {


    return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
    return RandomStringUtils.randomNumeric(10);
    }


    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException("NO_USER_FOUND_BY_USERNAME" + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException("USERNAME_ALREADY_EXISTS");
            }
            if(userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException("EMAIL_ALREADY_EXISTS");
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException("USERNAME_ALREADY_EXISTS");
            }
            if(userByNewEmail != null) {
                throw new EmailExistException("EMAIL_ALREADY_EXISTS");
            }
            return null;
        }
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


    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        return null;
    }

    @Override
    public void deleteUser(String username) throws IOException {
        User user = userRepository.findUserByUsername(username);
        Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        userRepository.deleteById(user.getId());

    }

    @Override
    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {

    }

    @Override
    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        return null;
    }
}

















































