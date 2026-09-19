package org.ong.dryforest.service.person;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Person;

public interface PersonService {
    
    Person findPersonById(int id_person);

    List<Person> findAllPersons();

    List<Person> findAllPersonsUpdatedSince(LocalDateTime last_sync);

    List<Person> findAllPersonsByRole(int id_role);
    
    Person createPerson(Person person);

    Person updatePerson(Person person);

    void deletePerson(int id_person);

}
