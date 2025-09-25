// src/main/java/br/com/doceria/liutis/SecurityConfig.java

package br.com.doceria.liutis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Ativa a configuração de CORS que definimos no bean abaixo
                .cors(Customizer.withDefaults())
                // Desativa a proteção CSRF, comum para APIs stateless
                .csrf(csrf -> csrf.disable())
                // Define as regras de autorização para os endpoints
                .authorizeHttpRequests(authorize -> authorize
                        // Permite que qualquer um (permitAll) acesse os produtos via GET
                        .requestMatchers(HttpMethod.GET, "/api/produtos/**").permitAll()
                        // Exige autenticação para qualquer outra requisição
                        .anyRequest().authenticated()
                )
                // Habilita a autenticação básica (usuário e senha)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // 2. Define as regras de CORS (quem pode acessar nossa API)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Adicione aqui as URLs do seu front-end.
        // É crucial ter a do ambiente de desenvolvimento (localhost) e, no futuro, a de produção.
        configuration.setAllowedOrigins(List.of("http://localhost:5173" /* , "https://seu-frontend.vercel.app" */));
        // Métodos HTTP que o front-end pode usar
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Cabeçalhos que o front-end pode enviar
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Permite o envio de credenciais (como cookies ou tokens de autenticação)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica estas regras de CORS para todos os endpoints da nossa API ("/**")
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("rafathalia")
                .password(passwordEncoder().encode("dog2025."))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
