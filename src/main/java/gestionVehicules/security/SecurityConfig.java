package gestionVehicules.security;

import gestionVehicules.model.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authemnticationProvider;


    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/**","/annonce/getimagesbyId/")
                .permitAll()

                .requestMatchers(HttpMethod.POST,"/SendMessage")
                .hasAuthority(String.valueOf(Role.USER))
                .requestMatchers(HttpMethod.POST,"/carburant","/categorie","/couleur","/marque","/modele","/pays")
//                crud rehetra
                .hasAuthority(String.valueOf(Role.ADMIN))
                .requestMatchers(HttpMethod.GET,"/carburant","/categorie","/couleur","/marque","/modele","/pays")
                .permitAll()
                .requestMatchers("/statistiques/**")
                .permitAll()
                .requestMatchers(HttpMethod.PUT,"/annonce/validateAnnonce")
                .hasAuthority(String.valueOf(Role.ADMIN))
//                .requestMatchers(HttpMethod.PUT,"/statistiques/**")
//                .authenticated()
//                .requestMatchers(HttpMethod.DELETE,"/statistiques/**")
//                .authenticated()
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
