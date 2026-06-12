package lexico.digital.solicitudes.taller.global.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntidad, Long>{

	Optional<UsuarioEntidad> findByIdentidad(String identidad);
}
