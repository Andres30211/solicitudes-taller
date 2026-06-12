package lexico.digital.solicitudes.taller.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;

public interface UsuarioServiciosRepositorio {
	
	List<UsuarioEntidad> list();
	
	Optional<UsuarioEntidad> updateRolUser(String identificacion);

	Optional<UsuarioEntidad> findByIdentidad(String nombre);
	
	ResponseEntity<UsuarioEntidad> save(UsuarioEntidad usuarioEntidad);
}
