package it.epicode.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Address;
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

}
