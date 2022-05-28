package cl.andres.java.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cl.andres.java.security.model.Categoria;
import cl.andres.java.security.repository.CategoriaRepository;

@Controller
public class SitioController {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	// Nada mas entrar a la raiz del programa, crea una categoria de ejemplo.
	@GetMapping("/")
	public String index() {
		long count = categoriaRepository.count();
		if(count == 0) {
			Categoria categoria = Categoria.builder().nombre("Categoria de ejemplo").build();
			categoriaRepository.saveAndFlush(categoria);
		}
		return "index";
	}
}
