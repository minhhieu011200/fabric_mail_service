package com.example.mail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mail_service.dto.ResponseCreateTemplateEmail;
import com.example.mail_service.dto.ResponseSendFileEmailDTO;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.service.TemplateEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TemplateEmailController {
	@Autowired
	TemplateEmailService templateEmailService;

	@PostMapping("/createTemplate")
	public TemplateEmail createTemplate(@RequestBody ResponseCreateTemplateEmail createTemplateEmail) {
		return templateEmailService.createTemplateEmail(createTemplateEmail);
	}

}
