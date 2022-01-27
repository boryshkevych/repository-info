package app.repositoryinfo.controller;

import app.repositoryinfo.dto.RepositoryDto;
import app.repositoryinfo.exception.handler.ApiError;
import app.repositoryinfo.service.repository.RepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(RepositoryController.class)
class RepositoryControllerTest {
    private static ParameterizedTypeReference<List<RepositoryDto>> REPOSITORY_LIST = new ParameterizedTypeReference<>() {
    };

    @MockBean
    RepositoryService repositoryService;

    @Autowired
    WebTestClient webClient;

    @Test
    void testRepositoriesAreReturned() {
        var repositories = List.of(RepositoryDto.builder()
                .name("testrepo")
                .ownerLogin("testOwner")
                .build());

        when(repositoryService.getUserRepositories(anyString())).thenReturn(Mono.just(repositories));

        webClient.get()
                .uri("/repositories/{userName}", "testUser")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(REPOSITORY_LIST).value(repositoryDtos -> Assertions.assertIterableEquals(repositories, repositoryDtos));
    }

    @Test
    void testXmlIsNotAcceptable() {
        webClient.get()
                .uri("/repositories/{userName}", "testUser")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void testExceptionHandler() {
        when(repositoryService.getUserRepositories(anyString())).thenThrow(NotFound.class);

        webClient.get()
                .uri("/repositories/{userName}", "testUser")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody(ApiError.class).value(error -> assertEquals(404, error.getStatus()));
    }

}