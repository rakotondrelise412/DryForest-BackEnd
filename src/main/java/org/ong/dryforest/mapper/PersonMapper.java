package org.ong.dryforest.mapper;

import org.ong.dryforest.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.person.PersonWebDTO;

public class PersonMapper {


    public static PersonWebDTO toWebDTO(Person person) {
        PersonWebDTO personDTO = new PersonWebDTO();

        personDTO.setId(person.getId());
        personDTO.setLast_name(person.getLast_name());
        personDTO.setFirst_name(person.getFirst_name());
        personDTO.setEmail(person.getEmail());
        personDTO.setPhone_number(person.getPhone_number());
        personDTO.setAddress(person.getAddress());
        personDTO.set_deleted(person.isDeleted());
        personDTO.setCreated_at(person.getCreatedAt());
        personDTO.setUpdated_at(person.getUpdatedAt());
        personDTO.setGender(person.getGender());
        personDTO.setRole(person.getRole());
        if (person.getSite() != null) {
            personDTO.setSite(SiteMapper.toWebDTO(person.getSite()));
        }
        
        return personDTO;
    }

    public static List<PersonWebDTO> toDTOList(List<Person> persons) {
        List<PersonWebDTO> personsDTO = new ArrayList<>();
        
        personsDTO = persons.stream()
                     .map(PersonMapper::toWebDTO)
                     .collect(Collectors.toList());

        return personsDTO;
    }

}