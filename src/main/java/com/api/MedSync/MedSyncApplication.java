package com.api.MedSync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
		info = @Info(title = "MedSync API", version = "v1"),
		servers = {
				@Server(url = "https://medsync-api.up.railway.app", description = "Railway API Server")
		}
)
@SpringBootApplication
@EnableScheduling
public class MedSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedSyncApplication.class, args);
	}

}
