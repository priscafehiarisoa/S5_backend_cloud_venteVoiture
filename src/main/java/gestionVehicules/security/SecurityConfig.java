package gestionVehicules.security;

import gestionVehicules.model.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authemnticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**")
                .permitAll()

                .requestMatchers("/SendMessage")
                .authenticated()
                .requestMatchers(HttpMethod.POST,"/carburant","/categorie","/couleur","/marque","/modele","/pays")
//                crud rehetra
                .hasAuthority(String.valueOf(Role.ADMIN))
                .requestMatchers(HttpMethod.GET,"/carburant","/categorie","/couleur","/marque","/modele","/pays")
                .permitAll()
                .requestMatchers("/statistiques/*")
                .hasAuthority(String.valueOf(Role.ADMIN))
//                .permitAll()
//                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authemnticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
