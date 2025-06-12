package com.empipe.kafka;

import com.empipe.model.Employee;
import com.empipe.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeKafkaConsumer {

    private final EmployeeService employeeService;

    @KafkaListener(
            topics = "employee-data",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeEmployeeData(
            @Payload List<Employee> employees,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Received batch of {} employees from partition {} at offset {}", 
                employees.size(), partition, offset);
        
        try {
            List<Employee> savedEmployees = employeeService.saveEmployees(employees);
            log.info("Successfully processed {} employees", savedEmployees.size());
        } catch (Exception e) {
            log.error("Error processing employee batch: {}", e.getMessage(), e);
            // Here you would typically implement retry logic or dead letter queue handling
        }
    }
} 