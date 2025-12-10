package com.phamtra.identity_service.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import com.phamtra.identity_service.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {


    @Value("${phamtra.jwt.base64-secret}")
    private String jwtKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                            CustomAuthenicationEntryPoint customAuthenicationEntryPoint) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .cors(Customizer.withDefaults())
                 .authorizeHttpRequests(authz -> authz
                         .requestMatchers("/", "/login").permitAll()
                         //.anyRequest().authenticated()
                         .anyRequest().authenticated()
                 )
                 .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())
                         .authenticationEntryPoint(customAuthenicationEntryPoint))
                 .exceptionHandling(
                         exceptions -> exceptions
                                 .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) //401
                                 .accessDeniedHandler(new BearerTokenAccessDeniedHandler())) //403

                 .formLogin(f -> f.disable())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         return http.build();
     }

     private SecretKey getSecretKey() {
         byte[] keyBytes = Base64.from(jwtKey).decode();
         return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
     }

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("http://localhost:5173");
        corsConfiguration.addAllowedOrigin("http://localhost:3002");
        corsConfiguration.addAllowedOrigin("http://localhost:3003");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
     }

     @Bean
     public JwtEncoder jwtEncoder() {
         return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
     }

     @Bean
     public JwtAuthenticationConverter jwtAuthenticationConverter() {
         JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
         grantedAuthoritiesConverter.setAuthorityPrefix("");
         grantedAuthoritiesConverter.setAuthoritiesClaimName("phamtra");

         JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
         return jwtAuthenticationConverter;
     }


     @Bean
     public JwtDecoder jwtDecoder() {
         NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                 getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
         return token -> {
             try {
                 return jwtDecoder.decode(token);
             } catch (Exception e) {
                 System.out.println(">>> JWT error: " + e.getMessage());
                 throw e;
             }
         };
     }




}
