package lexico.digital.solicitudes.taller.global.entidades;

import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lexico.digital.solicitudes.taller.global.entidades.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudEntidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombreCliente;
	
	@NotEmpty
	private String nombreProducto;
	
	@NotNull
	private Integer cantidad;
	
	private Instant fechaSolicitud;
	
	@Enumerated(EnumType.STRING)
	private EstadoEnum estadoEnum;
	
	@NotEmpty
	private String ordenCompraPdf;
	
	@NotEmpty
	private String planoPdf;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario")
	private UsuarioEntidad usuarioEntidad;
	
	public void prepersist() {
		this.fechaSolicitud = Instant.now();
	}
}
