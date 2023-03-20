package it.epicode.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Ad;
import it.epicode.capstone.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findByUsernameIgnoreCase(String username);
    public User findByUsername(String username);

}
