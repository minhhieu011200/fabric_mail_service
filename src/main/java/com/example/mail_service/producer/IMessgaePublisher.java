package com.example.mail_service.producer;

public interface IMessgaePublisher {
  void publish(String topic,String payload);
}
