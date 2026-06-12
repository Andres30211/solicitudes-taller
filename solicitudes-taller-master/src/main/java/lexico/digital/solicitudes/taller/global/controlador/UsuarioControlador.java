package lexico.digital.solicitudes.taller.global.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;
import lexico.digital.solicitudes.taller.global.repository.UsuarioServiciosRepositorio;
import org.springframework.web.bind.annotation.PutMapping;


@CrossOrigin(origins = {"https://bright-haupia-8d07b9.netlify.app", "http://localhost:4200"})
@RequestMapping("/api/usuario/global")
@RestController
public class UsuarioControlador {

	@Autowired
	private UsuarioServiciosRepositorio usuarioServiciosRepositorio;
	
	@PostMapping("/publico/registrar")
	public ResponseEntity<UsuarioEntidad> save(@RequestBody @Validated UsuarioEntidad usuarioEntidad){
		return this.usuarioServiciosRepositorio.save(usuarioEntidad);
	}
	
	@GetMapping("/privado/{identidad}")
	public ResponseEntity<UsuarioEntidad> findByIdentidad(@PathVariable("identidad") String identidad){
		return ResponseEntity.ok(this.usuarioServiciosRepositorio.findByIdentidad(identidad).get());
		
	}
	
	@PutMapping("/privado/update/{identidad}")
	ResponseEntity<UsuarioEntidad> updateRolUser(@PathVariable("identidad") String identidad){
		return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioServiciosRepositorio.updateRolUser(identidad).get());
	}
	
	@GetMapping("/privado/listar")
	public List<UsuarioEntidad> list(){
		return this.usuarioServiciosRepositorio.list();
	}
}
