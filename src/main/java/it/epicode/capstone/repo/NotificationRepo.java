package it.epicode.capstone.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
	@Query(
			nativeQuery = true,
			value = "SELECT * FROM notifications WHERE notifications.read = false AND notifications.user_id = :u")
	List<Notification> findUnreadNotifications(@Param("u") int id);
	
}
