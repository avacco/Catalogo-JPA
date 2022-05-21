package cl.andres.java.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.andres.java.security.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
