package com.example.mail_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mail_service.dto.ResponseCreateTemplateEmail;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.repository.TemplateEmailRepository;

@Service
public class TemplateEmailService {
	@Autowired
	TemplateEmailRepository templateEmailRepository;

	public TemplateEmail createTemplateEmail(ResponseCreateTemplateEmail createTemplateEmail) {

		TemplateEmail templateEmail = new TemplateEmail();
		return templateEmailRepository.save(templateEmail.builder().subject(createTemplateEmail.getSubject())
				.file(createTemplateEmail.getFile()).template(createTemplateEmail.getTemplate()).build());
	}
}
