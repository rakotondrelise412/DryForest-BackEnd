package org.ong.dryforest.service.user;

import org.ong.dryforest.entity.Users;
import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;

public interface UserService {
    
    Users findUsersById(int id_user);
    
    Users findUsersByUsername(String username);
    
    String generateUsername(String role);

    Users registerUsers(RegisterRequestDTO user);

    Users updatePassword(PasswordUpdateRequestDTO request);

    void deleteUsers(Users user);

}
