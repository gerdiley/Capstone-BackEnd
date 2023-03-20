package it.epicode.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.entity.Profile;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.ProfileRepo;

@Service
public class ProfileService {
	 @Autowired
	 ProfileRepo pr;
	 
//	 public List<User> getUserByUsername(String username){
//		 return pr.findProfileByUsername(username);
//	 }
}
