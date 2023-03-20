package it.epicode.capstone.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import it.epicode.capstone.entity.Profile;


@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
	
	
}
