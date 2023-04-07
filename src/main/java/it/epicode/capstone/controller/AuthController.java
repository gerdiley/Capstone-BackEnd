package it.epicode.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import it.epicode.capstone.config.JwtUtils;
import it.epicode.capstone.config.UserDetailsImpl;
import it.epicode.capstone.entity.Profile;
import it.epicode.capstone.entity.Role;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.login.LoginRequest;
import it.epicode.capstone.login.LoginResponse;
import it.epicode.capstone.repo.ProfileRepo;
import it.epicode.capstone.repo.RoleRepo;
import it.epicode.capstone.repo.UserRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired(required = true)
    AuthenticationManager authManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder pe;

    @Autowired
    RoleRepo rp;

    @Autowired
    UserRepo ur;
    
    @Autowired
    ProfileRepo pr;

    
    // ------------- LOGIN -----------------------
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    	
    	
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        

        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        
        User u = ur.findByUsername(((UserDetailsImpl)auth.getPrincipal()).getUsername());
        
        if(u.isActive()) {
        	String jwt = jwtUtils.generateJwtToken(auth);
            List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

            return ResponseEntity.ok(new LoginResponse(jwt,user.getUsername(), roles, user.getExpirationTime()));
        } else
        {
        	return (ResponseEntity<?>)ResponseEntity.badRequest();
        }
        
        
    }
    
    
    // ----------------------   SIGN UP --------------------

    @PostMapping("/auth/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(rp.findById(2).get());

        user.setRoleList(roles);
        user.setActive(true);
        user.setPassword(pe.encode(user.getPassword()));
        user.setRegistrationDate(LocalDate.now());
        
        Profile p = new Profile();
        p.setImg("https://cdn-icons-png.flaticon.com/512/149/149071.png");
        pr.save(p);
        
        user.setProfile(p);
        
        
        User u = ur.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    
    // ------------------- GET ALL USERS -----------------------

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return ur.findAll();
    }
    
    
    // ------------------- BAN USER ---------------------------
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> switchStatus(@RequestBody User user, @PathVariable int id) {
    	User u = ur.findById(id).get();
    	
    	if(u.isActive()) {
    	u.setActive(false);
    	} else {
    		u.setActive(true);
    	};
    	
    	ur.save(u);
    	
    	return new ResponseEntity<>(u, HttpStatus.OK);
    	
    }
    


}
