package com.codewithjay.scm0_2.Service;

import com.codewithjay.scm0_2.entities.User;

import java.util.Optional;

public interface UserService  {

    User saveUsers(User user);
    Optional<User> getUserById(int id);
    User getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    User upateUser(User user);

    void deleteUser(int id);

    boolean isUserExistsBYEmail(String emailId);

    boolean isUserExists(int id);

    Optional<User> getAllUser ();





}
