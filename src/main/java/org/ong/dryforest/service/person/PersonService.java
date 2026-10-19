package org.ong.dryforest.service.person;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.dto.person.PersonMobileDTO;
import org.ong.dryforest.dto.person.PersonWebDTO;
import org.ong.dryforest.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    Person findPersonById(int id_person);

    Page<Person> findAllPersons(Pageable pageable);

    List<Person> findAllPersonsUpdatedSince(LocalDateTime last_sync);

    List<Person> findAllPersonsByRole(int id_role);

    Person createPerson(PersonWebDTO personDTO);

    Person updatePerson(int id, PersonWebDTO personDTO);

    void deletePerson(int id_person);

    Person createPerson(PersonMobileDTO personDTO);

}
