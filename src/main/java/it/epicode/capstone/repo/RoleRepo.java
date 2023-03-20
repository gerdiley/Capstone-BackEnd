package it.epicode.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
}
