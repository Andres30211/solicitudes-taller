package lexico.digital.solicitudes.taller.global.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import lexico.digital.solicitudes.taller.global.entidades.SolicitudEntidad;

public interface SolicitudServicesRepository {
	
	ResponseEntity<String> entregarSolicitud(Long id);

	ResponseEntity<SolicitudEntidad> save(String nombreCliente, String nombreProducto, Integer cantidad, MultipartFile ordenCompraPdf, MultipartFile planoPdf , String identidad);
	
	ResponseEntity<List<SolicitudEntidad>> listaSolicitudes();
}
