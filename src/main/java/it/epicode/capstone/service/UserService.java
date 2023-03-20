package it.epicode.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo ur;
	
	public User findByUsername(String username) {
		return ur.findByUsername(username);
	}
	
	public Optional<User> findById(int id) {
		return ur.findById(id);
	}
}
