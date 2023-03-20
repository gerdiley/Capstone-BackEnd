package it.epicode.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.capstone.repo.AddressRepo;

@Service
public class AddressService {

	@Autowired
	AddressRepo ar;
}
