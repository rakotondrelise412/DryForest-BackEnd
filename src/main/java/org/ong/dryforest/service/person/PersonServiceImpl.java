package org.ong.dryforest.service.person;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.Person;
import org.ong.dryforest.repository.PersonRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findPersonById(int id_person) {
        return personRepository.findById(id_person)
                .orElseThrow(() -> new RuntimeException("Personne '" + id_person + "' introuvable"));
    }

    @Override
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> findAllPersonsUpdatedSince(LocalDateTime last_sync) {
        return personRepository.findAllUpdatedSince(last_sync);
    }

    @Override
    public List<Person> findAllPersonsByRole(int id_role) {
        return personRepository.findAllByRole_Id(id_role);
    }

    @Override
    public Person createPerson(Person person) {
        try {
            return personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Personne déjà existant");
        }
    }

    @Override
    public Person updatePerson(Person person) {
        findPersonById(person.getId());

        return personRepository.save(person);
    }

    @Override
    public void deletePerson(int id_person) {
        Person person = findPersonById(id_person);

        try {
            personRepository.delete(person);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cette personne");
        }
    }
 
}
