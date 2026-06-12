package lexico.digital.solicitudes.taller.global.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;
import lexico.digital.solicitudes.taller.global.entidades.enums.RolEnum;
import lexico.digital.solicitudes.taller.global.repository.UsuarioRepository;
import lexico.digital.solicitudes.taller.global.repository.UsuarioServiciosRepositorio;

@Service
public class UsuarioServiciosImpl implements UsuarioServiciosRepositorio{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Optional<UsuarioEntidad> findByIdentidad(String identidad) {
		
		return Optional.of(this.usuarioRepository.findByIdentidad(identidad).orElseThrow(() -> new UsernameNotFoundException("No existe")));
	}

	@Override
	public ResponseEntity<UsuarioEntidad> save(UsuarioEntidad usuarioEntidad) {
		
		long totalRol = this.usuarioRepository.count();
		
		if(totalRol == 0) {
			usuarioEntidad.setRol(RolEnum.JEFE_TALLER);
		}else {
			usuarioEntidad.setRol(RolEnum.VENDEDOR);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioRepository.save(usuarioEntidad));
	}

	@Override
	public Optional<UsuarioEntidad> updateRolUser(String identificacion) {
		
		return this.findByIdentidad(identificacion)
				.map(usuarioDb ->{
					usuarioDb.setRol(RolEnum.JEFE_TALLER);
					return Optional.of(this.usuarioRepository.save(usuarioDb));
				})
				.orElseThrow(() -> new RuntimeException("No existe ese Usuario para actualizar su rol..."));
	}

	@Override
	public List<UsuarioEntidad> list() {
		
		return this.usuarioRepository.findAll();
	}

	

}
