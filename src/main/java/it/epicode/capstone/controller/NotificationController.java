package it.epicode.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.capstone.entity.Ad;
import it.epicode.capstone.entity.Notification;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.NotificationRepo;
import it.epicode.capstone.service.NotificationService;
import it.epicode.capstone.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {
	@Autowired
	NotificationService ns;
	
	@Autowired
	UserService us;
	
	@GetMapping("/unread")
	public List<Notification> getUnreadNotification(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user        
        User currentUser = us.findByUsername(currentPrincipalName);
        
		return ns.findUnread(currentUser.getId());
	}
	
	@PutMapping("/{notification_id}")
	public ResponseEntity<Notification> switchNotification(@PathVariable int notification_id ,@RequestBody Notification notification){
		
		Notification currentNotification = ns.findById(notification_id).get();
		
		currentNotification.setRead(true);
		
        ns.saveNotification(currentNotification);
        
		return new ResponseEntity<> (currentNotification, HttpStatus.CREATED);
	}
	
	
}
