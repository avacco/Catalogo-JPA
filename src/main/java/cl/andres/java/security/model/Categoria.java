package cl.andres.java.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	@Column(nullable = false, length = 30, unique = true)
	@Size(min = 1, max = 30)
	private String nombre;
}
