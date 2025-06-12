package com.empipeline.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEventConsumer.class);

    @Value("${kafka.topic.employee}")
    private String employeeTopic;

    @KafkaListener(topics = "${kafka.topic.employee}", groupId = "employee-pipeline-group")
    public void consumeEmployeeEvent(EmployeeEventProducer.EmployeeEvent event) {
        logger.info("Received employee event: {} for employee: {}", 
            event.getEventType(), 
            event.getEmployee().getEmail());
        
        // Here you can add additional processing logic for employee events
        // For example, sending notifications, updating analytics, etc.
    }
} 