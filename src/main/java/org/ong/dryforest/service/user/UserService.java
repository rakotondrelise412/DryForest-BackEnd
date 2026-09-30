package org.ong.dryforest.service.user;

import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;
import org.ong.dryforest.dto.user.UserUpdateDTO;
import org.ong.dryforest.entity.Users;

import java.util.List;

public interface UserService {

    Users findUsersById(int id_user);

    Users findUsersByUsername(String username);

    List<Users> findAllUsers();

    String generateUsername(String role);

    Users registerUsers(RegisterRequestDTO user);

    Users updateUsers(int id_user, UserUpdateDTO request);

    Users updatePassword(PasswordUpdateRequestDTO request);

    void deleteUsers(Users user);
}