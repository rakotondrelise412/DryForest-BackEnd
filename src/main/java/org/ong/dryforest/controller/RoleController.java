package org.ong.dryforest.controller;

import org.ong.dryforest.dto.role.RoleDTO;
import org.ong.dryforest.entity.Role;
import org.ong.dryforest.mapper.RoleMapper;

import org.ong.dryforest.service.role.RoleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {

        List<Role> roles = roleService.findAll();

        return ResponseEntity.ok(
                RoleMapper.toDTOList(roles)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(
            @PathVariable int id
    ) {

        Role role = roleService.findById(id);

        return ResponseEntity.ok(
                RoleMapper.toDTO(role)
        );
    }


    @PostMapping
    public ResponseEntity<RoleDTO> create(
            @RequestBody RoleDTO roleDTO
    ) {

        Role role = RoleMapper.toEntity(roleDTO);

        Role savedRole = roleService.createRole(role);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RoleMapper.toDTO(savedRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> update(
            @PathVariable int id,
            @RequestBody RoleDTO roleDTO
    ) {

        roleDTO.setId(id);

        Role role = RoleMapper.toEntity(roleDTO);

        Role updatedRole = roleService.updateRole(role);

        return ResponseEntity.ok(
                RoleMapper.toDTO(updatedRole)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id
    ) {

        Role role = roleService.findById(id);

        roleService.deleteRole(role);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sync")
    public ResponseEntity<List<RoleDTO>> sync(
            @RequestParam("last_sync")
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime lastSync
    ) {

        List<Role> roles =
                roleService.findAllRoleUpdatedSince(lastSync);

        return ResponseEntity.ok(
                RoleMapper.toDTOList(roles)
        );
    }
}