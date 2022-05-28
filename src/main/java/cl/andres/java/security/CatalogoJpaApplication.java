package cl.andres.java.security;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cl.andres.java.security.controller.FileUploadUtils;

@SpringBootApplication
public class CatalogoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoJpaApplication.class, args);
		
		// Al iniciar el programa, elimina la carpeta de imagenes y todos sus contenidos si esta existia. 
		try {
			FileUploadUtils.cleanDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
