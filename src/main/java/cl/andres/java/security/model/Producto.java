package cl.andres.java.security.model;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	@Column(nullable = false, length = 50)
	@Size(min = 1, max = 50)
	private String nombre;
	private String imagen;
	@Size(max = 200)
	private String descripcion;
	@ManyToOne
	private Categoria categoria;
	
	
	@Transient
	public String getDirImagen() {
		if(imagen == null || id == null) return null;
		
		return "/imagenes/" + id + imagen ;
	}
}
