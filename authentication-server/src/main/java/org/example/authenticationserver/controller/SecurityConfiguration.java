package org.example.resourceserver.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Конфигурация сервера авторизации
 */
@Configuration
public class SecurityConfiguration {
    /**
     * Создание объекта пользователя c определенными параметрами для теста.
     * @return UserDetails
     */
    @Bean
    UserDetailsService inMemoryUserDetailsManager(){
        //Создаем "Постройщик" пользователя
        var userBuilder = User.builder();

        //Указываем детали парамеров пользователей
        UserDetails user = userBuilder
                .username("user") //имя user
                .password("111") //пароль
                .roles("USER", "ADMIN") //роли
                .build();

        //вовзращаем параметризированного пользователя
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Отключение шифрования пароля (только в учебных целях)
     * @return объект PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
