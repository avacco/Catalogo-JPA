package cl.andres.java.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.andres.java.security.model.Categoria;
import cl.andres.java.security.model.Producto;
import cl.andres.java.security.repository.CategoriaRepository;
import cl.andres.java.security.repository.ProductoRepository;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping("/nuevo")
	public String nuevo(Producto producto, Model modelo) {
		List<Categoria> categorias = categoriaRepository.findAll();
		modelo.addAttribute("categorias",categorias);
		return "producto/form";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable(name = "id") Producto producto, Model modelo) {
		List<Categoria> categorias = categoriaRepository.findAll();
		modelo.addAttribute("categorias",categorias);
		modelo.addAttribute("producto",producto);
		return "producto/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name = "id") Long id) {
		productoRepository.deleteById(id);
		return "redirect:/producto/listado";
	}
	
	@PostMapping("/procesar")
	public String procesar(@Valid Producto producto, BindingResult validacion) {
		if(validacion.hasErrors()) return "producto/form";
		
		productoRepository.saveAndFlush(producto);
		return "redirect:/producto/listado";
	}
	
	@GetMapping("/listado")
	public String listado(Model modelo) {
		List<Producto> productos = productoRepository.findAll();
		modelo.addAttribute("productos",productos);
		return "producto/listado";
	}
}
