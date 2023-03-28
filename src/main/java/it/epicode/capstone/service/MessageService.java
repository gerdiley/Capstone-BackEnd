package it.epicode.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.repo.MessageRepo;

@Service
public class MessageService {
	@Autowired
	MessageRepo mr;

}
