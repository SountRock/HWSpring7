package org.example.authenticationserver.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

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
                .password("password") //пароль
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

    /**
     * ПОПЫТКА при нажатии на "sing in" сразу вывовести страницу с котом
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                //HttpServletRequest request, HttpServletResponse response, String url
                //redirectStrategy.sendRedirect(request, response, "http://127.0.0.1:8080");
                response.sendRedirect("http://127.0.0.1:8080");
                //response.sendRedirect("/cat-page");
            }
        };
    }
}
