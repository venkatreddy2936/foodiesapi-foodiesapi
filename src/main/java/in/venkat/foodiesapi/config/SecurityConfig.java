package in.venkat.foodiesapi.config;


import in.venkat.foodiesapi.exception.CustomAccessDeniedHandler;
import in.venkat.foodiesapi.exception.CustomAuthenticationEntryPoint;
import in.venkat.foodiesapi.filters.JwtAuthenticationFilter;
import in.venkat.foodiesapi.service.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final AppUserDetailService userDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;



//    auhhentications for every user

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/register", "/api/login", "/api/contact", "/api/forgot/password", "/api/verify/otp").permitAll()

                        // Public read-only foods
                        .requestMatchers(HttpMethod.GET, "/api/foods", "/api/foods/**").permitAll()

                        // USER-only
                        .requestMatchers(HttpMethod.POST, "/api/cart", "/api/cart/remove").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/api/cart").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/api/orders/place", "/api/orders/verify").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/{orderId}").hasAuthority("ROLE_USER")

                        // ADMIN-only
                        .requestMatchers(HttpMethod.GET, "/api/orders/all").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/orders/status/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/foods").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/foods/**").hasAuthority("ROLE_ADMIN")

//                        this is for frontend purpose for temporary
//                                .requestMatchers(HttpMethod.POST, "/api/foods").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/api/foods/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/orders/all").permitAll()
//                                .requestMatchers(HttpMethod.PATCH, "/api/orders/status/**").permitAll()

                        // Any other request
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return   new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter(corsConfigurationSource());
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config =new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5174"));
        config.setAllowedMethods(List.of("GET","POST", "PUT","DELETE","OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);


        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",config);
            return source;

    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

}
