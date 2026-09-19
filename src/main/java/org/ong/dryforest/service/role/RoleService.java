package org.ong.dryforest.service.role;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Role;

public interface RoleService {

    List<Role> findAll();

    Role findById(int id);

    List<Role> findAllRoleUpdatedSince(LocalDateTime last_sync);

    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Role role);
    
}
