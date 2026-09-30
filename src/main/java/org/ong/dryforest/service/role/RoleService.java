package org.ong.dryforest.service.role;

import org.ong.dryforest.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(int id);

    List<Role> findAllRoleUpdatedSince(LocalDateTime lastSync);

    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Role role);
}