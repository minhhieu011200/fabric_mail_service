package com.example.mail_service.repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mail_service.entities.TemplateEmail;

@Repository
public interface TemplateEmailRepository extends JpaRepository<TemplateEmail, Long>{

}
