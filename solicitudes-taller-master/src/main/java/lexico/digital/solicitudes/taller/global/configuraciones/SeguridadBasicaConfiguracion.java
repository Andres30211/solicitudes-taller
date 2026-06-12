package lexico.digital.solicitudes.taller.global.configuraciones;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lexico.digital.solicitudes.taller.global.servicios.UsuarioSecurityService;

@Configuration
public class SeguridadBasicaConfiguracion {
	
	@Autowired
	private UsuarioSecurityService usuarioSecurityService;
	
	@Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(usuarioSecurityService);

        //provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
	        .csrf(csrf -> csrf.disable())
	        .headers(headers ->
	            headers.frameOptions(frame -> frame.disable())
	        )
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            .requestMatchers("/api/usuario/global/publico/**").permitAll()
	            .requestMatchers("/uploads/**").permitAll()
	            .requestMatchers("/h2-console/**").permitAll()
	            .requestMatchers("/api/solicitudes/taller/global/privado/**").authenticated()
	            .requestMatchers("/api/usuario/global/privado/**").authenticated()
	            .anyRequest().authenticated()
	        )
	        .httpBasic(Customizer.withDefaults())
	        .authenticationProvider(authenticationProvider());

	    return httpSecurity.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return this.usuarioSecurityService;
		
	}
	
	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
}
