package org.ong.dryforest.service.user;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.ong.dryforest.entity.Users;
import org.ong.dryforest.entity.Person;
import org.ong.dryforest.dto.auth.PasswordUpdateRequestDTO;
import org.ong.dryforest.dto.auth.RegisterRequestDTO;
import org.ong.dryforest.repository.UsersRepository;
import org.ong.dryforest.service.person.PersonService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PersonService personService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users findUsersById(int id_user) {
        return usersRepository.findById(id_user)
                .orElseThrow(() -> new RuntimeException("Utilisateur '" + id_user + "' introuvable"));
    }

    @Override
    public Users findUsersByUsername(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur '" + username + "' introuvable"));
    }

    @Override
    public String generateUsername(String role) {
        String normalized = Normalizer.normalize(role, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        normalized = pattern.matcher(normalized).replaceAll("");

        String prefix = normalized.toUpperCase().substring(0, Math.min(3, normalized.length()));
        String lastUsername = usersRepository.findLastUsernameByPrefix(prefix);

        int counter = 1;
        if (lastUsername != null) {
            String numberPart = lastUsername.replace(prefix, "");
            try {
                counter = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException e) {
                counter = 1;
            }
        }

        String username = prefix + String.format("%04d", counter);

        return username;
    }

    @Override
    public Users registerUsers(RegisterRequestDTO user) {
        Person person = personService.findPersonById(user.getId_person());
        String username = generateUsername(person.getRole().getName());

        Users new_user = new Users();
        new_user.setUsername(username);
        new_user.setPerson(person);
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            return usersRepository.save(new_user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Utilisateur déjà existant");
        }
    }
    
    @Override
    public Users updatePassword(PasswordUpdateRequestDTO request) {  
        Users user = findUsersByUsername(request.getUsername());

        if (request.getOldPassword() != null &&
            !passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        String new_password = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(new_password);

        return usersRepository.save(user);
    }

    @Override
    public void deleteUsers(Users user) {
        findUsersById(user.getId());

        try {
            usersRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Impossible de supprimer cet utilisateur");
        }
    }
    
}
