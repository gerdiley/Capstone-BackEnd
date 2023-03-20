package it.epicode.capstone.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.epicode.capstone.entity.Ad;
import it.epicode.capstone.entity.ImageModel;
import it.epicode.capstone.entity.Profile;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.AdRepo;
import it.epicode.capstone.repo.UserRepo;
import it.epicode.capstone.service.AdService;
import it.epicode.capstone.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdController {
	@Autowired
	UserService us;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	AdService as;
	@Autowired
	AdRepo ar;
	
	
	
	//----------- ADD AN AD -------------
	
	@PostMapping("/ads")
	public ResponseEntity<?> addAd(@RequestBody Ad ad){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        ad.setUser(us.findByUsername(currentPrincipalName));
        
        Ad a = ar.save(ad);
        
		return new ResponseEntity<> (ad, HttpStatus.CREATED);
	}
//	@PostMapping(value= "/ads", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//	public ResponseEntity<?> addAd(@RequestPart("ad") Ad ad,
//									@RequestPart("imageFile") MultipartFile[] file){
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String currentPrincipalName = authentication.getName();
//		
//		ad.setUser(us.findByUsername(currentPrincipalName));
//		
//		// Ad a = ar.save(ad);
//		
//		
//		try {
//		Set<ImageModel> images =	uploadImage(file);
//		ad.setProductImages(images);
//		Ad a = ar.save(ad);
//		return new ResponseEntity<> (ad, HttpStatus.CREATED);
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//			return null;
//		}
//		
//	}
//	
//	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
//		Set<ImageModel> imageModels = new HashSet<>();
//		for(MultipartFile file: multipartFiles) {
//			ImageModel imageModel = new ImageModel(
//					file.getOriginalFilename(),
//					file.getContentType(),
//					file.getBytes()
//					);
//			imageModels.add(imageModel);
//			
//		}
//		
//		return imageModels;
//	}
	
	//------------ GET ADS ----------------
	
	@GetMapping("/ads")
	public Page<Ad> getall(@RequestParam int page, @RequestParam int size, Pageable pageable){
		return as.getAll(pageable);
	}

	//------------ GET AN AD----------------
	
	@GetMapping("/ads/{id}")
	public Optional<Ad> getById(@PathVariable int id) {
		return as.getById(id);
	}
	//------------ EDIT AN AD ---------------
	
	@PutMapping("/ads/edit/{id}")
	public ResponseEntity<Ad> updateAd(@PathVariable int id ,@RequestBody Ad ad){
		
		Ad currentAd = ar.findById(id).get();
		
		if(ad.getDescription() ==  "" || ad.getDescription() ==  null) {
			currentAd.setDescription(currentAd.getDescription());
		} else {
			currentAd.setDescription(ad.getDescription());
		}
		
		if(ad.getImg() == "" || ad.getImg() == null) {
			currentAd.setImg(currentAd.getImg());
		} else {
			currentAd.setImg(ad.getImg());
		}
		
//		if(ad.getCategory() == null) {
//			currentAd.setCategory(currentAd.getCategory());
//		} else {
//			currentAd.setCategory(ad.getCategory());
//		}
		
		if(ad.getExpirationDate() == null ) {
			currentAd.setExpirationDate(currentAd.getExpirationDate());
		} else
		{
			currentAd.setExpirationDate(ad.getExpirationDate());
		}
		
		if(ad.getTitle() == "" || ad.getTitle() == null) {
			currentAd.setTitle(currentAd.getTitle());
		} else {
			currentAd.setTitle(ad.getTitle());
		}
		
        ar.save(currentAd);
        
		return new ResponseEntity<> (currentAd, HttpStatus.CREATED);
	}
	
	//-------------DELETE AN AD-----------------
	
	@DeleteMapping("/ads/{id}")
	public ResponseEntity<?> deleteAd(@PathVariable int id){
		as.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//******************* FILTERS **************	
	
	//-------------------FILTER BY TITLE-----------
	@GetMapping("/ads/title")
	public List<Ad> filterByname(@RequestParam String title){
		return ar.findByTitleContaining(title);
	}
	
	// -------------------FILTER BY USERNAME-------
	@GetMapping("/ads/username")
	public List<Ad> findByUsername(@RequestParam String username){
		return as.findByUsername(username);
	}
	
	// -------------------FILTER BY CATEGORY-------
	@GetMapping("/ads/category")
	public List<Ad> findByCategory(@RequestParam String category){
		return as.findByCategory(category);
	}
	//-------------------- FILTER TOPS --------------
	
	@GetMapping("/ads/tops")
	public List<Ad> getTops(){
		return as.getTops();
	}
	
	//**********************LIKES********************
	
	
	@PutMapping("/ads/like/{id}")
	public ResponseEntity<Ad> addLike(@PathVariable int id ,@RequestBody Ad ad){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        
        User u = us.findByUsername(currentPrincipalName);
        
        Ad a = as.getById(id).get();
        
        List<Profile> likes = a.getLikesList() ;

        
        if(a.getLikesList().contains(u.getProfile())) {
        	a.setLikes(a.getLikes() - 1);
        	likes.remove(u.getProfile());
        } else {	
        	a.setLikes(a.getLikes() + 1);
        	likes.add(u.getProfile()); 
        	 }
		
        a.setLikesList(likes);
        ar.save(a);
		return new ResponseEntity<> (a, HttpStatus.CREATED);
	}
}
