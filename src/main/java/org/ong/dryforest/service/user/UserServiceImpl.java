package org.ong.dryforest.service.user;

import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;
import org.ong.dryforest.dto.user.UserUpdateDTO;
import org.ong.dryforest.entity.Person;
import org.ong.dryforest.entity.Users;
import org.ong.dryforest.repository.UsersRepository;

import org.ong.dryforest.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PersonService personService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public Page<Users> findAllUsers(Pageable pageable) {
        return usersRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Users findUsersById(int id_user) {
        return usersRepository.findByIdAndIsDeletedFalse(id_user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur '" + id_user + "' introuvable"
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Users findUsersByUsername(String username) {
        return usersRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Utilisateur '" + username + "' introuvable"
                        )
                );
    }

    @Override
    @Transactional
    public void deleteUsers(Users user) {
        Users existing = findUsersById(user.getId());
        existing.setDeleted(true);
        usersRepository.save(existing);
    }

    @Override
    public String generateUsername(String role) {

        String normalized =
                Normalizer.normalize(role, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{M}");

        normalized =
                pattern.matcher(normalized).replaceAll("");

        String prefix =
                normalized
                        .toUpperCase()
                        .substring(0, Math.min(3, normalized.length()));

        String lastUsername =
                usersRepository.findLastUsernameByPrefix(prefix);

        int counter = 1;

        if (lastUsername != null) {

            String numberPart =
                    lastUsername.replace(prefix, "");

            try {

                counter =
                        Integer.parseInt(numberPart) + 1;

            } catch (NumberFormatException ignored) {

            }
        }

        return prefix + String.format("%04d", counter);
    }


    @Override
    public Users registerUsers(RegisterRequestDTO user) {

        Person person =
                personService.findPersonById(
                        user.getId_person()
                );

        String username =
                generateUsername(
                        person.getRole().getName()
                );

        Users new_user = new Users();

        new_user.setUsername(username);

        new_user.setPerson(person);

        new_user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        try {

            return usersRepository.save(new_user);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Utilisateur déjà existant"
            );
        }
    }


    @Override
    public Users updateUsers(
            int id_user,
            UserUpdateDTO request) {

        Users user =
                findUsersById(id_user);

        if (request.getId_person() != 0) {

            Person person =
                    personService.findPersonById(
                            request.getId_person()
                    );

            user.setPerson(person);
        }

        if (request.getUsername() != null &&
                !request.getUsername().isBlank()) {

            user.setUsername(
                    request.getUsername()
            );
        }

        try {

            return usersRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException(
                    "Username déjà utilisé"
            );
        }
    }


    @Override
    public void updatePassword(
            PasswordUpdateRequestDTO request) {

        Users user =
                findUsersByUsername(
                        request.getUsername()
                );

        if (request.getOldPassword() != null &&
                !passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword())) {

            throw new RuntimeException(
                    "Ancien mot de passe incorrect"
            );
        }

        String new_password =
                passwordEncoder.encode(
                        request.getNewPassword()
                );

        user.setPassword(new_password);

        usersRepository.save(user);
    }

}