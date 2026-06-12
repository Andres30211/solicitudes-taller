package lexico.digital.solicitudes.taller.global.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lexico.digital.solicitudes.taller.global.entidades.UsuarioEntidad;
import lexico.digital.solicitudes.taller.global.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioSecurityService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String identidad) throws UsernameNotFoundException {
		
		UsuarioEntidad usuario = this.usuarioRepository.findByIdentidad(identidad)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getIdentidad())
                .password("{noop}".concat(usuario.getContrasena()))
                .roles(usuario.getRol().toString())
                .build();
    
	}

	

}
