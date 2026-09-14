package pe.utp.vidplagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Base de Spring Security para escalar el proyecto mas adelante (login,
 * roles, JWT, etc.).
 *
 * Por ahora queda deliberadamente permisiva: todas las rutas son publicas
 * y CSRF esta deshabilitado, para no romper las pruebas con curl ni el
 * envio del formulario mientras no exista autenticacion real. Cuando se
 * agregue login, este filtro es el punto donde se restringen rutas con
 * .requestMatchers(...).authenticated() y se registra el AuthenticationProvider.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); // permite la consola H2

        return http.build();
    }
}
