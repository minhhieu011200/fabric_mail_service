package com.example.mail_service.service;

import java.util.Iterator;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MailService implements ISendMail {
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String emailFrom;

	public void sendMail(String to, String subject, String content, MultipartFile[] files) throws MessagingException {
		log.info("22222222222222222222222voday");
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
}
