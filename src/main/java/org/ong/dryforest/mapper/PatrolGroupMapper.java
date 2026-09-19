package org.ong.dryforest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.patrolGroup.PatrolGroupDTO;
import org.ong.dryforest.entity.PatrolGroup;

public class PatrolGroupMapper {
    public static PatrolGroupDTO toPatrolGroupDTO(PatrolGroup patrolGroup){
        PatrolGroupDTO patrolGroupDTO = new PatrolGroupDTO();

        patrolGroupDTO.setId_patrol_group(patrolGroup.getId());
        patrolGroupDTO.setName(patrolGroup.getName());

        return patrolGroupDTO;
    }

    public static List<PatrolGroupDTO> toDTOList(List<PatrolGroup> patrolGroups){
        List<PatrolGroupDTO> patrolGroupDTO = new ArrayList<>();

        patrolGroupDTO = patrolGroups.stream().map(PatrolGroupMapper::toPatrolGroupDTO).collect(Collectors.toList());

        return patrolGroupDTO;
    }
}
