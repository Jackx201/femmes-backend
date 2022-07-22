package com.sistema.blog.configuracion;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

	@KafkaListener(topics = "onis", groupId = "test1")
	void listener(String data) {
		System.out.print("Listener Received: " + data + " :D ");
	}
}
