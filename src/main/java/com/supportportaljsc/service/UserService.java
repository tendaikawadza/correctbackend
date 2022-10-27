package com.supportportaljsc.service;

import com.supportportaljsc.domain.User;
import com.supportportaljsc.exception.domain.EmailExistException;
import com.supportportaljsc.exception.domain.EmailNotFoundException;
import com.supportportaljsc.exception.domain.UserNotFoundException;
import com.supportportaljsc.exception.domain.UsernameExistException;
import com.supportportaljsc.service.impl.NotAnImageFileException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException,
            UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser (String firstName, String LastName, String username, String Email, String role, boolean  isNotLocked, boolean isActive, MultipartFile profileImage, String NO_USER_FOUND_BY_USERNAME, String USERNAME_ALREADY_EXISTS, String EMAIL_ALREADY_EXISTS) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;


    User upAddUser(String currentUserName, String newFirstName, String newLastName, String newusername, String newEmail, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage);

    void delete(long id);

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void resetPassword (String email) throws MessagingException, EmailNotFoundException
            ;
      User updateProfileImage(String username,    MultipartFile    PrfofileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;


    void deleteUser(String username) throws IOException;
}
