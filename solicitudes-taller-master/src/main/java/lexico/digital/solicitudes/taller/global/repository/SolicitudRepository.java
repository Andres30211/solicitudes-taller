package lexico.digital.solicitudes.taller.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lexico.digital.solicitudes.taller.global.entidades.SolicitudEntidad;

@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudEntidad, Long>{

}
