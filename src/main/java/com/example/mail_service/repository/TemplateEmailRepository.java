package com.example.mail_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mail_service.entities.TemplateEmail;

@Repository
public interface TemplateEmailRepository extends JpaRepository<TemplateEmail, Long>{
	
//	@EntityGraph(attributePaths = { "templateEmail" })
	Optional<TemplateEmail> findByTemplate(String template);
}
