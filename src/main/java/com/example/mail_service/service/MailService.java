package com.example.mail_service.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.mail_service.dto.SendFileEmailDTO;
import com.example.mail_service.entities.EmailLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MailService implements ISendMail {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String emailFrom;

	public void sendMail(String to, String subject, String content, MultipartFile[] files) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content);
		if (files != null) {
			for (MultipartFile multipartFile : files) {
				helper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
			}
		}
		helper.setFrom(emailFrom);
		mailSender.send(message);
	}

	public Boolean sendHtmlEmail(SendFileEmailDTO sendFileEmailDTO) throws MessagingException {
		ObjectMapper mapper = new ObjectMapper();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(sendFileEmailDTO.getToMail());
		helper.setSubject(sendFileEmailDTO.getSubject());

		Context context = new Context();

		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
		};

		Map<String, Object> templateModel;
		try {

			String dataTemplate;

			if (sendFileEmailDTO.getDataTemplate() instanceof String) {
				dataTemplate = sendFileEmailDTO.getDataTemplate().toString();
			} else {
				dataTemplate = mapper.writeValueAsString(sendFileEmailDTO.getDataTemplate());
			}
			
			templateModel = mapper.readValue(dataTemplate, typeRef);
			context.setVariables(templateModel);
			String htmlContent = templateEngine.process(sendFileEmailDTO.getFileTemplate(), context);

			helper.setText(htmlContent, true); // Set to true to indicate HTML content

			mailSender.send(message);
			return true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
}
