package it.epicode.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.epicode.capstone.entity.Ad;
import it.epicode.capstone.entity.Category;
import it.epicode.capstone.repo.AdRepo;

@Service
public class AdService {

	@Autowired
	AdRepo ar;
	
	public Page<Ad> getAll(Pageable pageable){
		return ar.findAll(pageable);
	}
	
	public List<Ad> getByTitle(String title){
		return ar.findByTitleContaining(title);
	}
	
	public Optional<Ad> getById(int id) {
		return ar.findById(id);
	}
	
	public Optional<Ad> deleteById(int id){
		
		Optional<Ad> ad = ar.findById(id);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
		
        if(ad.get().getUser().getUsername().equals(currentPrincipalName)) {
        	ar.delete(ad.get());
        }
		
		
		return ad;
	}
	
	public List<Ad> findByUsername(String username){
		return ar.findByUsername(username); 
	}
	
	public List<Ad> findByCategory(String category){
		return ar.findByCategory(category);
	}
	
	public List<Ad> getTops(){
		return ar.findTops();
	}
	
	public List<Ad> getFavourites(int ProfileId){
		return ar.findFavouritesByProfileId(ProfileId);
	}
	
	public void removeFavourite(int profileId, int ad_id) {
		ar.deleteFavouriteByProfileIdAndAdId(profileId, ad_id);
	}

	
}
