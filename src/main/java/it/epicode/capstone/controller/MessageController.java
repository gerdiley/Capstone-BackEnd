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

import it.epicode.capstone.entity.Message;
import it.epicode.capstone.entity.Review;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.MessageRepo;
import it.epicode.capstone.service.MessageService;
import it.epicode.capstone.service.UserService;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class MessageController {
	@Autowired
	MessageService ms;
	@Autowired
	MessageRepo mr;
	@Autowired
	UserService us;
	
	//	MESSAGGI INVIATI DALL'UTENTE LOGGATO ALL'UTENTE SPECIFICATO
	@GetMapping("/sent")
	public List<Message> getSentMessages(@RequestParam int rec_id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user        
        User currentUser = us.findByUsername(currentPrincipalName);
        
       return mr.findByRecipientSenderId(currentUser.getId(), rec_id );
       
	}
	
//	MESSAGGI RICEVUTI DALL'UTENTE LOGGATO DA parte dell'UTENTE SPECIFICATO
	@GetMapping("/received")
	public List<Message> getReceivedMessages(@RequestParam int sender_id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		// logged user        
		User currentUser = us.findByUsername(currentPrincipalName);
		
		return mr.findByRecipientSenderId(sender_id, currentUser.getId() );
		
	}
	
	
	@PostMapping("/{recipient_id}")
	public ResponseEntity<?> sendMessage(@RequestBody Message message, @PathVariable int recipient_id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user        
        User currentUser = us.findByUsername(currentPrincipalName);
        // user to give a review        
        Optional<User> u = us.findById(recipient_id);
        
        message.setSender(currentUser);
        
        message.setRecipient(u.get());
        
        message.setDate(LocalDate.now());
       
        mr.save(message);
        
        return new ResponseEntity<> (message, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
    public List<Message> getAllMessages(@RequestParam int rec_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = us.findByUsername(currentPrincipalName);

        return mr.findAllMessages(currentUser.getId(), rec_id );

    }
}
