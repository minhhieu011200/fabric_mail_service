package com.example.mail_service.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class SMSService implements ISendMail {
	@Value("${twilio.phone.number}")
	private String fromPhoneNumber;


	@Override
	public void sendMail(String to, String subject, String content, MultipartFile[] files) throws MessagingException {
		Message.creator(new PhoneNumber(to),
				new PhoneNumber(fromPhoneNumber),
				content).create();
	}

}
