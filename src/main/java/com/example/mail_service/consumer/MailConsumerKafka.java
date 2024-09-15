package com.example.mail_service.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	@Autowired
	private ExecutorService executorService; // Create a thread pool

	@KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.groupid}")
	public void listen(ConsumerRecord<String, String> records) {
		try {
			String message = records.value();
			executorService.submit(() -> processListener(message));
		} catch (Exception e) {

		}

	}

	public void processListener(String message) {
		log.info("Thread: " + Thread.currentThread().getName() + " message: " + message);

		try {
			processMessage(message);
		} catch (JsonProcessingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("Thread error" + e.getMessage());
			Thread.currentThread().interrupt();
		}

	}

	public void processMessage(String message)
			throws JsonMappingException, JsonProcessingException, MessagingException, InterruptedException {
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseSendFileEmailDTO messageObj = objectMapper.readValue(message, ResponseSendFileEmailDTO.class);
		if (messageObj.getTemplate() != null && !messageObj.getTemplate().isEmpty()) {
			EmailLog newEmailLog = emailLogService.createEmailLog(messageObj);
			try {
				emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.SENDING);

				SendFileEmailDTO sendFileEmailDTO = new SendFileEmailDTO();
				sendFileEmailDTO.setToMail(newEmailLog.getToMail());
				sendFileEmailDTO.setSubject(newEmailLog.getTemplateEmail().getSubject());
				sendFileEmailDTO.setFileTemplate(newEmailLog.getTemplateEmail().getFile());

				sendFileEmailDTO.setDataTemplate(newEmailLog.getDataTemplate());

				Boolean checkSendMail = mailService.sendHtmlEmail(sendFileEmailDTO);
				if (checkSendMail) {
					emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.SENDED);
				}
			} catch (Exception e) {
				emailLogService.updateEmailLog(newEmailLog.getId(), EnumStatusMail.FAILED);
			}

		}
	}
}
