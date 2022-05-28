package cl.andres.java.security.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
		// Utilizando el id requerido, hace una busqueda y retorna el nombre del archivo de imagen a eliminar
		String dir = productoRepository.findImagenById(id);
		// Luego lo pasa al metodo deleteFile junto a la ruta y la id del archivo
		FileUploadUtils.deleteFile("imagenes/"+id.toString()+dir);
		// Hecho esto, elimina el registro por completo y redirecciona al listado
		productoRepository.deleteById(id);		
		return "redirect:/producto/listado";
	}
	
	@PostMapping("/procesar")
	public String procesar(@Valid Producto producto, BindingResult validacion, @RequestParam("image") MultipartFile multipartFile, Model modelo) throws IOException {
		if(validacion.hasErrors()) {
			List<Categoria> categorias = categoriaRepository.findAll();
			modelo.addAttribute("categorias",categorias);
			return "producto/form";
		}
		
		// limpia la ruta del archivo subido
		String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		nombreArchivo = StringUtils.trimAllWhitespace(nombreArchivo); // quita espacios
		
		producto.setImagen(nombreArchivo);
		
		String dirSubida = "imagenes";
		Producto productoProcesado = productoRepository.saveAndFlush(producto);
		FileUploadUtils.saveFile(dirSubida, (productoProcesado.getId()+nombreArchivo), multipartFile);
		
		
		return "redirect:/producto/listado";
	}
	
	@GetMapping("/listado")
	public String listado(Model modelo) {
		List<Producto> productos = productoRepository.findAll();
		modelo.addAttribute("productos",productos);
		return "producto/listado";
	}
}
