package app.repositoryinfo.service.repository;

import app.repositoryinfo.dto.RepositoryDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RepositoryService {

    Mono<List<RepositoryDto>> getUserRepositories(String userName);

}
