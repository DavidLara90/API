package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.demo.dto.UserDTO;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	private String baseUrl = "http://659c6af7633f9aee79079fc6.mockapi.io/api/v1";

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public List<UserDTO> getUser() {
        return webClient.get()
                .uri("/usuario")
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();
    }

    public UserDTO saveUser(UserDTO user) {
        webClient.post()
                .uri("/usuario")
                .body(Mono.just(user), UserDTO.class)
                .retrieve()
                .toBodilessEntity()
                .block();
		return user;
    }

    public UserDTO updateUser(Integer id, UserDTO user) {
        webClient.put()
                .uri("/usuario/{id}", id)
                .body(Mono.just(user), UserDTO.class)
                .retrieve()
                .toBodilessEntity()
                .block();
		return user;
    }

    public boolean deleteUser(Integer id) {
        try {
            webClient.delete()
                    .uri("/usuario/{id}", id)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (Exception e) {
            
            e.printStackTrace();
            return false;
        }
    }
    
    public UserDTO getUserById(Integer id) {
        return webClient.get()
                .uri(baseUrl + "/usuario/{id}", id)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }
}
