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
            personDTO.setSite(
                    SiteMapper.toWebDTO(person.getSite())
            );
        } else {
            personDTO.setSite(null);
        }

        return personDTO;
    }


    public static List<PersonWebDTO> toDTOList(List<Person> persons) {

        return persons.stream()
                .map(PersonMapper::toWebDTO)
                .collect(Collectors.toList());
    }


    public static Person toEntity(PersonWebDTO personDTO) {

        Person person = new Person();

        person.setId(personDTO.getId());

        person.setLast_name(personDTO.getLast_name());
        person.setFirst_name(personDTO.getFirst_name());
        person.setEmail(personDTO.getEmail());
        person.setPhone_number(personDTO.getPhone_number());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());
        person.setRole(personDTO.getRole());

        return person;
    }
}
