package com.example.mail_service.service;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;

public interface ISendMail {
	void sendMail(String to, String subject, String content, MultipartFile[] files) throws MessagingException;
}
