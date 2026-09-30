package org.ong.dryforest.service.role;

import org.ong.dryforest.entity.Role;
import org.ong.dryforest.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {

        return roleRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Role findById(int id) {

        return roleRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new RuntimeException("Role introuvable")
                );
    }

    @Override
    public List<Role> findAllRoleUpdatedSince(LocalDateTime lastSync) {

        return roleRepository.findAllUpdatedSince(lastSync);
    }

    @Override
    public Role createRole(Role role) {

        try {

            role.setId(0);

            return roleRepository.save(role);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Rôle déjà existant"
            );
        }
    }

    @Override
    public Role updateRole(Role role) {

        Role existingRole = findById(role.getId());

        existingRole.setName(role.getName());

        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Role role) {

        Role existingRole = findById(role.getId());

        try {
            existingRole.setDeleted(true);

            roleRepository.save(existingRole);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalStateException(
                    "Impossible de supprimer ce rôle"
            );
        }
    }
}