package com.example.BatchSystem;


import com.example.BatchSystem.storage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BatchSystemApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatchSystemApplication.class, args);

	}

}
