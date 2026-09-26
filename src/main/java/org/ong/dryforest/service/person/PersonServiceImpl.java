package org.ong.dryforest.service.person;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.dto.person.PersonWebDTO;
import org.ong.dryforest.entity.Gender;
import org.ong.dryforest.entity.Person;
import org.ong.dryforest.entity.Role;
import org.ong.dryforest.entity.Site;
import org.ong.dryforest.repository.GenderRepository;
import org.ong.dryforest.repository.PersonRepository;

import org.ong.dryforest.repository.RoleRepository;
import org.ong.dryforest.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SiteRepository siteRepository;

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
    public List<Person> findAllActivePersons() {
        return personRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Person createPerson(PersonWebDTO dto) {

        try {

            Person person = new Person();

            person.setLast_name(dto.getLast_name());
            person.setFirst_name(dto.getFirst_name());
            person.setEmail(dto.getEmail());
            person.setPhone_number(dto.getPhone_number());
            person.setAddress(dto.getAddress());


            if (dto.getGender() != null) {

                int idGender = dto.getGender().getId();

                Gender gender = genderRepository
                        .findById(idGender)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Gender '" + idGender + "' introuvable"
                                )
                        );

                person.setGender(gender);
            }


            if (dto.getRole() != null) {

                int idRole = dto.getRole().getId();

                Role role = roleRepository
                        .findById(idRole)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role '" + idRole + "' introuvable"
                                )
                        );

                person.setRole(role);
            }


            if (dto.getSite() != null) {

                int idSite = dto.getSite().getId();

                Site site = siteRepository
                        .findById(idSite)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Site '" + idSite + "' introuvable"
                                )
                        );

                person.setSite(site);
            }

            return personRepository.save(person);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    e.getMostSpecificCause().getMessage()
            );
        }
    }

    @Override
    public Person updatePerson(int id, PersonWebDTO personDTO) {

        Person person = findPersonById(id);

        person.setLast_name(personDTO.getLast_name());
        person.setFirst_name(personDTO.getFirst_name());
        person.setEmail(personDTO.getEmail());
        person.setPhone_number(personDTO.getPhone_number());
        person.setAddress(personDTO.getAddress());

        if (personDTO.getGender() != null) {
            int idGender = personDTO.getGender().getId();

            Gender gender = genderRepository.findById(idGender)
                    .orElseThrow(() ->
                            new RuntimeException("Gender '" + idGender + "' introuvable"));

            person.setGender(gender);
        }

        if (personDTO.getRole() != null) {
            int idRole = personDTO.getRole().getId();

            Role role = roleRepository.findById(idRole)
                    .orElseThrow(() ->
                            new RuntimeException("Role '" + idRole + "' introuvable"));

            person.setRole(role);
        }

        if (personDTO.getSite() != null) {
            int idSite = personDTO.getSite().getId();

            Site site = siteRepository.findById(idSite)
                    .orElseThrow(() ->
                            new RuntimeException("Site '" + idSite + "' introuvable"));

            person.setSite(site);
        } else {
            person.setSite(null);
        }

        return personRepository.save(person);
    }


    @Override
    public void deletePerson(int id_person) {

        Person person = findPersonById(id_person);

        try {
            personRepository.delete(person);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "Impossible de supprimer cette personne : "
                            + e.getMostSpecificCause().getMessage()
            );
        }
    }


}
