package com.example.mail_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SendFileEmailDTO {
	private String toMail;
	private String subject;
	private Object dataTemplate;
	private String fileTemplate;
}
