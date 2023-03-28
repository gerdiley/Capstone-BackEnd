package it.epicode.capstone.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Ad;

@Repository
public interface AdRepo extends JpaRepository<Ad, Integer> {
	
	List<Ad> findByTitleContaining(String title);
	
	@Query(
			nativeQuery = true,
			value = "SELECT * "
					+ "FROM ads "
					+ "INNER JOIN users ON ads.user_id = users.id "
					+ "WHERE users.username = :u")
	List<Ad> findByUsername(@Param("u") String username);
	
	@Query(
			nativeQuery = true,
			value = "SELECT * "
					+ "FROM ads "
					+ "WHERE ads.category = :c")
	List<Ad> findByCategory(@Param("c") String category);
	
	@Query(
			nativeQuery = true,
			value = "SELECT * FROM ads ORDER BY ads.likes DESC LIMIT 3")
	List<Ad> findTops();
	
	@Query(
			nativeQuery = true,
			value = "SELECT * "
					+ "FROM ads "
					+ "INNER JOIN users ON ads.user_id = users.id "
					+ "INNER JOIN profiles ON users.profile_id = profiles.id "
					+ "INNER JOIN ads_likes ON ads.id = ads_likes.ad_id "
					+ "WHERE ads_likes.profile_id = :p")
	List<Ad> findFavouritesByProfileId(@Param("p") int profileId);
	
	
	
	
}
