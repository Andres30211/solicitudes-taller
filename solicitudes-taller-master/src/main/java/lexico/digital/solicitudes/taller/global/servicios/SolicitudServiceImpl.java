package lexico.digital.solicitudes.taller.global.servicios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lexico.digital.solicitudes.taller.global.entidades.SolicitudEntidad;
import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;
import lexico.digital.solicitudes.taller.global.entidades.enums.EstadoEnum;
import lexico.digital.solicitudes.taller.global.repository.SolicitudRepository;
import lexico.digital.solicitudes.taller.global.repository.SolicitudServicesRepository;
import lexico.digital.solicitudes.taller.global.repository.UsuarioServiciosRepositorio;

@Service
public class SolicitudServiceImpl implements SolicitudServicesRepository{
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	@Autowired
	private UsuarioServiciosRepositorio usuarioServiciosRepositorio;
	
	@Autowired
    private TaskScheduler taskScheduler;

	/*@Override
	public ResponseEntity<SolicitudEntidad> save(SolicitudEntidad solicitudEntidad, String identidad) {
		
		UsuarioEntidad usuarioEntidad = this.usuarioServiciosRepositorio.findByIdentidad(identidad)
				.orElseThrow();
		
		solicitudEntidad.setEstadoEnum(EstadoEnum.CREADO);
		solicitudEntidad.setUsuarioEntidad(usuarioEntidad);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.solicitudRepository.save(solicitudEntidad));
	}*/

	@Override
	public ResponseEntity<List<SolicitudEntidad>> listaSolicitudes() {
		
		return ResponseEntity.ok(this.solicitudRepository.findAll());
	}

	@Override
	public ResponseEntity<String> entregarSolicitud(Long id) {
		
		SolicitudEntidad solicitud = this.solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstadoEnum(EstadoEnum.ENTREGADO);

        this.solicitudRepository.save(solicitud);

        taskScheduler.schedule(() -> {
        	eliminarArchivo(solicitud.getOrdenCompraPdf());
            eliminarArchivo(solicitud.getPlanoPdf());
            this.solicitudRepository.deleteById(id);
        }, Instant.now().plus(10, ChronoUnit.MINUTES));

        return ResponseEntity.ok(
                "La solicitud fue entregada, en 10 min será borrada."
        		
        );
	}
	
	private void eliminarArchivo(String ruta) {
	    try {
	        if (ruta == null || ruta.isEmpty()) return;

	        Path path = Paths.get(ruta);

	        Files.deleteIfExists(path);

	    } catch (IOException e) {
	        System.out.println("Error eliminando archivo: " + ruta);
	    }
	}

	@Override
	public ResponseEntity<SolicitudEntidad> save(String nombreCliente, String nombreProducto, Integer cantidad,
			MultipartFile ordenCompraPdf, MultipartFile planoPdf, String identidad) {
		
		UsuarioEntidad usuarioEntidad = this.usuarioServiciosRepositorio
	            .findByIdentidad(identidad)
	            .orElseThrow();

	    SolicitudEntidad solicitud = new SolicitudEntidad();
	    solicitud.setNombreCliente(nombreCliente);
	    solicitud.setNombreProducto(nombreProducto);
	    solicitud.setCantidad(cantidad);
	    solicitud.setEstadoEnum(EstadoEnum.CREADO);
	    solicitud.setUsuarioEntidad(usuarioEntidad);

	    // 📁 Guardar archivos
	    if (ordenCompraPdf != null && !ordenCompraPdf.isEmpty()) {
	        String rutaOrden = guardarArchivo(ordenCompraPdf, "ordenes");
	        solicitud.setOrdenCompraPdf(rutaOrden);
	    }

	    if (planoPdf != null && !planoPdf.isEmpty()) {
	        String rutaPlano = guardarArchivo(planoPdf, "planos");
	        solicitud.setPlanoPdf(rutaPlano);
	    }

	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(this.solicitudRepository.save(solicitud));
	}
	
	private String guardarArchivo(MultipartFile archivo, String carpeta) {

	    try {

	        String uploadDir = "uploads/" + carpeta + "/";

	        File directorio = new File(uploadDir);
	        if (!directorio.exists()) {
	            directorio.mkdirs();
	        }

	        String nombreOriginal = archivo.getOriginalFilename();

	        // ✅ Obtener extensión segura
	        String extension = "";
	        if (nombreOriginal != null && nombreOriginal.contains(".")) {
	            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
	        }

	        // 🔥 Nombre seguro
	        String nombreArchivo = UUID.randomUUID().toString() + extension;

	        Path ruta = Paths.get(uploadDir, nombreArchivo);

	        Files.write(ruta, archivo.getBytes());

	        return ruta.toString();

	    } catch (IOException e) {
	        throw new RuntimeException("Error al guardar archivo", e);
	    }
	}

}
