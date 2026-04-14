
package com.alibaba.cloud.ai.agentdatamanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DataAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAgentApplication.class, args);
	}

}
