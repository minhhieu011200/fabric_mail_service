package com.example.mail_service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.mail_service.dto.ResponseSendFileEmailDTO;
import com.example.mail_service.dto.SendFileEmailDTO;
import com.example.mail_service.entities.EmailLog;
import com.example.mail_service.entities.EnumStatusMail;
import com.example.mail_service.entities.TemplateEmail;
import com.example.mail_service.service.EmailLogService;
import com.example.mail_service.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class MailConsumerKafka {
	@Autowired
	private MailService mailService;

	@Autowired
	private EmailLogService emailLogService;

	@KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.groupid}")
	public void listen(ConsumerRecord<String, String> records) {
		try {
			String message = records.value();
			proccessListener(message);

		} catch (Exception e) {
			
		}

	}

	public void proccessListener(String message)
			throws JsonMappingException, JsonProcessingException, MessagingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseSendFileEmailDTO messageObj = objectMapper.readValue(message, ResponseSendFileEmailDTO.class);
		if (messageObj.getTemplateId() != null) {
			EmailLog newEmailLog = emailLogService.createEmailLog(messageObj);
			try {
				emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.SENDING);

				SendFileEmailDTO sendFileEmailDTO = new SendFileEmailDTO();
				sendFileEmailDTO.setToMail(newEmailLog.getToMail());
				sendFileEmailDTO.setSubject(newEmailLog.getTemplateEmail().getSubject());
				sendFileEmailDTO.setFileTemplate(newEmailLog.getTemplateEmail().getFile());

				
				sendFileEmailDTO.setDataTemplate(newEmailLog.getDataTemplate());

				Boolean checkSendMail = mailService.sendHtmlEmail(sendFileEmailDTO);
				if(checkSendMail) {
					emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.SENDED);
				}
			} catch (Exception e) {
				emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.FAILED);
			}

		}
	}
}
