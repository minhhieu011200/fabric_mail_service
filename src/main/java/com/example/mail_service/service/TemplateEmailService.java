package com.example.mail_service.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.mail_service.dto.ResponseCreateTemplateEmail;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.repository.TemplateEmailRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TemplateEmailService {
	@Autowired
	TemplateEmailRepository templateEmailRepository;
	private static String UPLOAD_DIR = "src/main/resources/templates/";

	public TemplateEmail createTemplateEmail(ResponseCreateTemplateEmail createTemplateEmail) {

		TemplateEmail templateEmail = new TemplateEmail();
		return templateEmailRepository.save(templateEmail.builder().subject(createTemplateEmail.getSubject())
				.file(createTemplateEmail.getFileName()).template(createTemplateEmail.getTemplate()).build());
	}

	public String saveFile(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}
		try {
			// Get the file and save it to the directory
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
			Files.write(path, bytes);

			return file.getOriginalFilename();
		} catch (IOException e) {
			log.error("Loi upload file" + e);
			return null;
		}

	}
}
