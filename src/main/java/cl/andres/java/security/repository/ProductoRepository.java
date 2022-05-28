package cl.andres.java.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.andres.java.security.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	// Retornara el nombre de la imagen correspondiente para su eliminacion
	@Query(value = "SELECT imagen FROM producto p WHERE p.id = ?", nativeQuery = true)
	String findImagenById(Long id);

}
