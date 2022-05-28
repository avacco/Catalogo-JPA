package cl.andres.java.security.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String eliminar(@PathVariable(name = "id") Long id) throws IOException {
		productoRepository.deleteById(id);		
		return "redirect:/producto/listado";
	}
	
	@PostMapping("/procesar")
	public String procesar(@Valid Producto producto, BindingResult validacion, @RequestParam("image") MultipartFile imagen, Model modelo) throws IOException {
		if(validacion.hasErrors()) {
			List<Categoria> categorias = categoriaRepository.findAll();
			modelo.addAttribute("categorias",categorias);
			return "producto/form";
		}
		if(producto.getId() == null) {
			byte[] contenidoImagen 	= imagen.getBytes();
			Producto agregarProducto = Producto.builder()
										.nombre(producto.getNombre())
										.imagen(contenidoImagen)
										.descripcion(producto.getDescripcion())
										.categoria(producto.getCategoria())
										.build();
			productoRepository.saveAndFlush(agregarProducto);
			return "redirect:/producto/listado";
		}else {
			byte[] contenidoImagen 	= imagen.getBytes();
			producto.setImagen(contenidoImagen);
			productoRepository.saveAndFlush(producto);
			return "redirect:/producto/listado";
		}
	}
	
	@GetMapping("/listado")
	public String listado(Model modelo) {
		List<Producto> productos = productoRepository.findAll();
		modelo.addAttribute("productos",productos);
		return "producto/listado";
	}
	
	// Retorna la imagen desde la base de datos usando ResponseEntity
		@GetMapping("listado/{id}")
		public ResponseEntity<byte[]> muestraImagenes(@PathVariable("id") Long id) throws SQLException {

			Optional<Producto> producto = productoRepository.findById(id);
			byte[] imageBytes = null;
			if (producto.isPresent()) {
				imageBytes = producto.get().getImagen();
			}

			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		}
	
	
	
}
