package com.api.MedSync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(title = "MedSync API", version = "v1"),
		servers = {
				@Server(url = "https://medsync-api.up.railway.app", description = "Railway API Server")
		}
)
@SpringBootApplication
public class MedSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedSyncApplication.class, args);
	}

}
