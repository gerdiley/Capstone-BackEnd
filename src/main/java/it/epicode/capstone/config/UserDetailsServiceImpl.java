package it.epicode.capstone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.UserRepo;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> loadByUsername = ur.findByUsernameIgnoreCase(username);

        if(loadByUsername.isPresent()) {
            return UserDetailsImpl.build(loadByUsername.get());
        } else {
            throw new UsernameNotFoundException("User not found" + username);
        }
    }
}
