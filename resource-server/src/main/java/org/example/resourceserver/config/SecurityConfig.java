package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /**
     * Правила фильтрации.
     * @param http (http запрос).
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(//параметры аунтификации
                authf -> authf.anyRequest().authenticated()//аутентификации всех запросов
                                  ).oauth2ResourceServer(//параметры oauth2 ресурс-сервера
                                          oauth2 -> oauth2.jwt(Customizer.withDefaults()) //устанавливаем использование jwt токена на этом сервере
                                          );
        return http.build();
    }

}
