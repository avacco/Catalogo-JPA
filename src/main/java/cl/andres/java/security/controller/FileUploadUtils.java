package cl.andres.java.security.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

// Esta clase se encarga de manejar las subidas de archivos.

public class FileUploadUtils {
     
    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        final List<String> tiposPermitidos = Arrays.asList("image/png", "image/jpeg");
        
        
        
        if(tiposPermitidos.contains(multipartFile.getContentType())) {   
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Error al guardar archivo: " + fileName, ioe);
	        }     
        } else {
        	throw new IOException("Tipo de archivo invalido: " + fileName);
        }
    }
}