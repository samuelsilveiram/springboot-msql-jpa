package br.com.updateapp.updateapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UpdateAppApplication {
	
	@Autowired
	private UpdateAppVerify updateAppVerify;
	
	public static void main(String[] args) {
		SpringApplication.run(UpdateAppApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	
        	updateAppVerify.executaAPorraDaConsulta();
      
        };
    }
	
}
