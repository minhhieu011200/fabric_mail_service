package com.example.mail_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mail_service.dto.ResponseSendFileEmailDTO;
import com.example.mail_service.entities.EmailLog;
import com.example.mail_service.entities.EnumStatusMail;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.repository.EmailLogRepository;
import com.example.mail_service.repository.TemplateEmailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EmailLogService {
	@Autowired
	EmailLogRepository emailLogRepository;

	@Autowired
	TemplateEmailRepository templateEmailRepository;

	public EmailLog createEmailLog(ResponseSendFileEmailDTO createEmailLog) {
		TemplateEmail templateEmail = templateEmailRepository.findByTemplate(createEmailLog.getTemplate())
				.orElseThrow(null);

		EmailLog emailLog = new EmailLog();
		
		if (templateEmail == null) {
			return null;
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String objString= objectMapper.writeValueAsString(createEmailLog.getDataTemplate());

			return emailLogRepository.save(emailLog.builder().toMail(createEmailLog.getToMail())
					.templateEmail(templateEmail).dataTemplate(objString)
					.status(EnumStatusMail.PENDING).build());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public EmailLog updateEmailLog(Long id, EnumStatusMail status) {
		return emailLogRepository.findById(id)
		        .map(emailLog -> {
		            emailLog.setStatus(status);
		            emailLogRepository.save(emailLog);
		            return emailLog;
		        })
		        .orElse(null);  // Return null if EmailLog not found
	}
}
