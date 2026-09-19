package org.ong.dryforest.security;

import org.ong.dryforest.entity.Users;
import org.ong.dryforest.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public CustomUserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));
        return new CustomUsers(user);
    }
}
