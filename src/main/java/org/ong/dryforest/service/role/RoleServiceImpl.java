package org.ong.dryforest.service.role;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Role;
import org.ong.dryforest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll(){ return roleRepository.findAllByIsDeletedFalse(); }

    @Override
    public Role findById(int id){
        return roleRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new RuntimeException("Role introuvable"));
    }

    @Override
    public List<Role> findAllRoleUpdatedSince(LocalDateTime last_sync) {
        return roleRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public Role createRole(Role role){
        try {
            return roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Rôle déjà existant");
        }
    }

    @Override
    public Role updateRole(Role role){
        findById(role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Role role){
        try {
            findById(role.getId());
            roleRepository.delete(role);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer ce rôle");
        }
    }
}