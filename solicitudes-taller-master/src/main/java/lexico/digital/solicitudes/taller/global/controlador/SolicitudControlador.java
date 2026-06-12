package lexico.digital.solicitudes.taller.global.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lexico.digital.solicitudes.taller.global.entidades.SolicitudEntidad;
import lexico.digital.solicitudes.taller.global.repository.SolicitudServicesRepository;

@CrossOrigin(origins = {"https://global-solicitudes-taller.netlify.app", "http://localhost:4200"})
@RestController
@RequestMapping("/api/solicitudes/taller/global")
public class SolicitudControlador {
	
	@Autowired
	private SolicitudServicesRepository solicitudServicesRepository;
	
	@PutMapping("/privado/entregar/{id}")
	public ResponseEntity<String> entregarSolicitud(@PathVariable("id") Long id){
		return this.solicitudServicesRepository.entregarSolicitud(id);
	}

	@GetMapping("/privado/listar")
	public ResponseEntity<List<SolicitudEntidad>> listaSolicitudes(){
		return this.solicitudServicesRepository.listaSolicitudes();
	}
	
	@PostMapping("/privado/save")
	public ResponseEntity<SolicitudEntidad> save(
			@RequestParam String nombreCliente,
			@RequestParam String nombreProducto,
			@RequestParam Integer cantidad,
			@RequestParam(required = false) MultipartFile ordenCompraPdf,
			@RequestParam(required = false) MultipartFile planoPdf,
			Authentication authentication){
		return this.solicitudServicesRepository.save(nombreCliente, nombreProducto, cantidad, ordenCompraPdf, planoPdf, authentication.getName());
	}
}
