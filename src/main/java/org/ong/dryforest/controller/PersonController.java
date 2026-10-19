package org.ong.dryforest.controller;

import java.util.List;

import org.ong.dryforest.dto.person.PersonMobileDTO;
import org.ong.dryforest.entity.Person;
import org.ong.dryforest.mapper.PersonMapper;
import org.ong.dryforest.dto.person.PersonWebDTO;
import org.ong.dryforest.service.person.PersonService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<Page<PersonWebDTO>> getAllPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<PersonWebDTO> personsDTO =
                personService.findAllPersons(pageable)
                        .map(PersonMapper::toWebDTO);

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

    @PostMapping("/mobile")
    public ResponseEntity<?> createPersonMobile(
            @RequestBody PersonMobileDTO personDTO) {

        try {
            Person createdPerson =
                    personService.createPerson(personDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(PersonMapper.toWebDTO(createdPerson));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPerson(
            @RequestBody PersonWebDTO personDTO) {

        try {
            Person createdPerson =
                    personService.createPerson(personDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(PersonMapper.toWebDTO(createdPerson));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(
            @PathVariable int id,
            @RequestBody PersonWebDTO personDTO){
        try {
            Person updatedPerson = personService.updatePerson(id, personDTO);
            return ResponseEntity.ok(
                    PersonMapper.toWebDTO(updatedPerson)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id) {

        try {
            personService.deletePerson(id);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

}