package com.empipeline.kafka;

import com.empipeline.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.employee}")
    private String employeeTopic;

    public void sendEmployeeEvent(EmployeeDTO employeeDTO, String eventType) {
        EmployeeEvent event = new EmployeeEvent(eventType, employeeDTO);
        kafkaTemplate.send(employeeTopic, employeeDTO.getEmail(), event);
    }

    public static class EmployeeEvent {
        private String eventType;
        private EmployeeDTO employee;

        public EmployeeEvent() {
        }

        public EmployeeEvent(String eventType, EmployeeDTO employee) {
            this.eventType = eventType;
            this.employee = employee;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public EmployeeDTO getEmployee() {
            return employee;
        }

        public void setEmployee(EmployeeDTO employee) {
            this.employee = employee;
        }
    }
} 