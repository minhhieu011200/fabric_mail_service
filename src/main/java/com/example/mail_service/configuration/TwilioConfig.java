package com.example.mail_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;

@Component
public class TwilioConfig {
//	@Value("${twilio.account.sid}")
	private String accountSid;

//	@Value("${twilio.auth.token}")
	private String authToken;


	public TwilioConfig(@Value("${twilio.account.sid}") String accountSid,
			@Value("${twilio.auth.token}") String authToken) {
		Twilio.init(accountSid, authToken);
	}
}
