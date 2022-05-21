package cl.andres.java.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.andres.java.security.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
