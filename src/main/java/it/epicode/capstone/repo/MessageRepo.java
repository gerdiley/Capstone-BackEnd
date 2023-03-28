package it.epicode.capstone.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.capstone.entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {
	
	@Query(
			nativeQuery= true,
			value="SELECT * "
					+ "FROM messages "
					+ "WHERE messages.recipient_id = :r "
					+ "AND messages.sender_id = :s"
			)
	List<Message> findByRecipientSenderId(@Param("s") int sender, @Param("r")int recipient);
	
	
	@Query(
            nativeQuery= true,
            value="SELECT * FROM messages " +
                    "WHERE messages.sender_id = :r " +
                    "AND messages.recipient_id = :s " +
                    "OR messages.sender_id = :s " +
                    "AND messages.recipient_id = :r"
    )
    List<Message> findAllMessages(@Param("s") int sender, @Param("r") int recipient);
}
