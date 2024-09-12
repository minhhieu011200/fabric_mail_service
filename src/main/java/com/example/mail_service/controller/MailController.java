package com.example.mail_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mail_service.dto.SendSMSDTO;
import com.example.mail_service.service.MailService;
import com.example.mail_service.service.SMSService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class MailController {
	@Autowired
	private MailService mailService;

	@Autowired
	private SMSService smsService;

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content,
			@RequestParam(required = false) MultipartFile[] files) {
		// TODO: process POST request
		try {
			mailService.sendMail(to, subject, content, files);
			return "Thanh cong";
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("222222222222222222222" + e.getMessage());
			return "That bai";
		}

	}

	@PostMapping("/sendSms")
	public String sendSms(@RequestBody SendSMSDTO sendSMSDTO) {
		try {
			log.info("2222222222222vonao"+sendSMSDTO.toString());
			
			smsService.sendMail(sendSMSDTO.getTo(), "", sendSMSDTO.getContent(), null);
			return "Thanh cong";
		} catch (MessagingException e) { // TODO Auto-generated catch block
			e.printStackTrace();
			log.error("222222222222222222222" + e.getMessage());
			return "That bai";
		}
	}

}
