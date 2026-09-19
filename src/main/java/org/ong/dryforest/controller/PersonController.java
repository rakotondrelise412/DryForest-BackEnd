package org.ong.dryforest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ong.dryforest.entity.Person;
import org.ong.dryforest.mapper.PersonMapper;
import org.ong.dryforest.dto.person.PersonWebDTO;
import org.ong.dryforest.service.person.PersonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonWebDTO>> getAllPersons() {
        List<PersonWebDTO> personsDTO = new ArrayList<>();

        List<Person> persons = personService.findAllPersons();
        if (persons != null && !persons.isEmpty()) {
            personsDTO = PersonMapper.toDTOList(persons);
        }

        return ResponseEntity.ok(personsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable int id){
        try {
            Person person = personService.findPersonById(id);
            PersonWebDTO personDTO = PersonMapper.toWebDTO(person);

            return ResponseEntity.ok(personDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/role/{id_role}")
    public ResponseEntity<List<PersonWebDTO>> getPersonsByRole(
            @PathVariable int id_role) {

        List<Person> persons =
                personService.findAllPersonsByRole(id_role);

        List<PersonWebDTO> personsDTO =
                PersonMapper.toDTOList(persons);

        return ResponseEntity.ok(personsDTO);
    }

}