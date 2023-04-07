package it.epicode.capstone.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.entity.Ad;
import it.epicode.capstone.entity.Address;
import it.epicode.capstone.entity.Profile;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.AddressRepo;
import it.epicode.capstone.repo.ProfileRepo;
import it.epicode.capstone.repo.UserRepo;
import it.epicode.capstone.service.ProfileService;
import it.epicode.capstone.service.UserService;

@RestController
@RequestMapping("/profiles")
@CrossOrigin
public class ProfileController {
	
	@Autowired
	ProfileRepo pr;
	@Autowired
	UserService us;
	
	@Autowired
	ProfileService ps;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	AddressRepo ar;
	
	// -------------------- ADD A PROFILE -----------------------
	
	@PostMapping("")
	public ResponseEntity<?> addProfile(@RequestBody Profile profile){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        User u = us.findByUsername(currentPrincipalName);
        
        u.setProfile(profile);
        Profile p = pr.save(profile);
        
        return new ResponseEntity<> (profile, HttpStatus.CREATED);
	}
	
	// ------------------ GET AUTHENTICATED USER -------------------	
	@GetMapping("/username")
	public User getProfile(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
		return us.findByUsername(currentPrincipalName);
	}
	
	// ------------------ GET USER BY USERNAME -------------------
	@GetMapping("/username1")
	public User getProfileByUsername(@RequestParam String username){

		return us.findByUsername(username);
	}
	
	// ------------------ GET USER BY ID -------------------
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable int id) {
		return ur.findById(id);
	}
	
	// ------------------ EDIT PROFILE -------------------
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> editProfile(@PathVariable int id , @RequestBody User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        User u = us.findByUsername(currentPrincipalName);
        
        // edit username
        if(user.getUsername() == null | user.getUsername() == "") {
        	u.setUsername(u.getUsername());
        } else {
        	u.setUsername(user.getUsername());
        }
        
        
        // edit img   
        if(user.getProfile().getImg() == null| user.getProfile().getImg() == "") {
        	u.getProfile().setImg(u.getProfile().getImg());
        } else {
        u.getProfile().setImg(user.getProfile().getImg());
        }
        
        // edit email
        if(user.getEmail() == null| user.getEmail() == "") {
        	u.setEmail(u.getEmail());
        } else {
        u.setEmail(user.getEmail());
        }
        
        // edit address        
        if(user.getAddress() == null) {
        	u.setAddress(u.getAddress());
        } else
        {
        	Address a = new Address();
            
            a.setCity(user.getAddress().getCity());
            a.setPostalCode(user.getAddress().getPostalCode());
            a.setStreet(user.getAddress().getStreet());
            
            ar.save(a);
            
        	u.setAddress(a);
        }
        
        ur.save(u);
        
        
        return new ResponseEntity<> (u, HttpStatus.CREATED);
	}
	
}
