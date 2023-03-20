package it.epicode.capstone.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {

}
