package com.example.mail_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailLog {
	private String to;
	private String attachment;
	private String content;
	
	private String file;
	private Object dataFile;
}
