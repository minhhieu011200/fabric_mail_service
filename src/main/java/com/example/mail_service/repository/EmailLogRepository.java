package com.example.mail_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mail_service.entities.EmailLog;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
	
	@EntityGraph(attributePaths = { "templateEmail" })
	@Override
//	@Query("SELECT p FROM Product p JOIN FETCH p.category where p.id=:id")
	Optional<EmailLog> findById(Long id);
}
