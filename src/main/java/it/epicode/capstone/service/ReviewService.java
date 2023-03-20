package it.epicode.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.repo.ReviewRepo;

@Service
public class ReviewService {
	@Autowired
	ReviewRepo rr;
	
}
