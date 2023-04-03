package it.epicode.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.entity.Notification;
import it.epicode.capstone.repo.NotificationRepo;

@Service
public class NotificationService {
	@Autowired
	NotificationRepo nr;
	
	public List<Notification> findUnread(int id){
		return nr.findUnreadNotifications(id);
	}
	
	public Optional<Notification> findById(int id){
		return nr.findById(id);
	}
	
	public void saveNotification(Notification n) {
		nr.save(n);
	}
}
