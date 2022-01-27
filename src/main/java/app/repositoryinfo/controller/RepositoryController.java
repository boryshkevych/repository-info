package app.repositoryinfo.controller;

import app.repositoryinfo.dto.RepositoryDto;
import app.repositoryinfo.service.repository.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
public class RepositoryController {

    private final RepositoryService repositoryService;

    @GetMapping("/{userName}")
    Mono<List<RepositoryDto>> getRepositories(@PathVariable String userName) {
        return repositoryService.getUserRepositories(userName);
    }

}
