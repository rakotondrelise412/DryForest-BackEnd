package org.ong.dryforest.service.user;

import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;
import org.ong.dryforest.dto.user.UserUpdateDTO;
import org.ong.dryforest.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Users findUsersById(int id_user);

    Users findUsersByUsername(String username);

    Page<Users> findAllUsers(Pageable pageable);

    String generateUsername(String role);

    Users registerUsers(RegisterRequestDTO user);

    Users updateUsers(int id_user, UserUpdateDTO request);

    void updatePassword(PasswordUpdateRequestDTO request);

    void deleteUsers(Users user);
}