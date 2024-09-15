package com.example.mail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.mail_service.dto.ResponseCreateTemplateEmail;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.service.TemplateEmailService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class TemplateEmailController {
	@Autowired
	TemplateEmailService templateEmailService;

	@PostMapping("/createTemplate")
	public TemplateEmail createTemplate(@ModelAttribute ResponseCreateTemplateEmail createTemplateEmail) {
		String fileNameString = templateEmailService.saveFile(createTemplateEmail.getFile());
		log.info("222222222222222222222222"+fileNameString);
		if (fileNameString == null || fileNameString.isEmpty()) {
			return null;
		}
		
		createTemplateEmail.setFileName(fileNameString);
		return templateEmailService.createTemplateEmail(createTemplateEmail);
	}

}
