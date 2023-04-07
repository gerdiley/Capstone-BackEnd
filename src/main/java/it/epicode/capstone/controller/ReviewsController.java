package it.epicode.capstone.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.entity.Review;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.ReviewRepo;
import it.epicode.capstone.service.ReviewService;
import it.epicode.capstone.service.UserService;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewsController {
	
	@Autowired
	ReviewService rs;
	@Autowired
	UserService us;
	
	@Autowired
	ReviewRepo rr;
	
	//---------- ADD A REVIEW --------------
	
	@PostMapping("/{user_id}")
	public ResponseEntity<?> addReview(@RequestBody Review review, @PathVariable int user_id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user        
        User currentUser = us.findByUsername(currentPrincipalName);
        // user to give a review        
        Optional<User> u = us.findById(user_id);
        
        
        review.setUser(currentUser);
        review.setDate(LocalDate.now());
        
        List<Review> reviews = u.get().getProfile().getReviews();
        reviews.add(review);
        
        u.get().getProfile().setReviews(reviews);
        
        rr.save(review);
        
        return new ResponseEntity<> (review, HttpStatus.CREATED);
        
        
	}
	
	//	-------- GET ALL REVIEWS OF AN USER ----------
	
	@GetMapping("/{user_id}")
	public List<Review> getReviews(@PathVariable int user_id){
		User u = us.findById(user_id).get();
		return u.getProfile().getReviews();	
	}
	
	
	
	
	
	
	
	
}
