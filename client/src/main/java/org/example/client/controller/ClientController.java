package org.example.resourceserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Base64;

@Controller
public class ClientController {
    /**
     * Объект для получения токена авторизации.
     */
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Получение представления с котом.
     * @param model
     * @param authorizedClient объект авторизации.
     * @return страничка с котом.
     */
    @GetMapping
    public String getCat(Model model, Principal authorizedClient) {
        //объект для выполнения запроса
        RestTemplate template = new RestTemplate();

        //Получение access токена
        String accessToken = authorizedClientService
                .loadAuthorizedClient("reg-client", authorizedClient.getName()) //загрузка авторизованного клиента
                .getAccessToken().getTokenValue(); //Получение access токена *

        //Создаем заголовок и помещаем в него токен авторизации
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        //Отправляем запрос (обращение к resource-server)
        ResponseEntity<byte[]> response =
                template.exchange("http://localhost:8081/cat", //адрес resource-server
                        HttpMethod.GET, entity, byte[].class);

        //Преобразование полученного изображения
        String base64Image = Base64.getEncoder().encodeToString(response.getBody());

        //Загрузка изображения на страницу
        model.addAttribute("cat", base64Image);

        return "cat-page";
    }
}
