package app.repositoryinfo.service.repository.github;

import app.repositoryinfo.dto.BranchDto;
import app.repositoryinfo.dto.RepositoryDto;
import app.repositoryinfo.service.repository.RepositoryService;
import app.repositoryinfo.service.repository.model.Branch;
import app.repositoryinfo.service.repository.model.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class GitHubRepositoryService implements RepositoryService {
    // @formatter:off
    private final static ParameterizedTypeReference<List<Repository>> REPOSITORY_LIST_TYPE = new ParameterizedTypeReference<>() {};
    private final static ParameterizedTypeReference<List<Branch>> BRANCH_LIST_TYPE = new ParameterizedTypeReference<>() {};
    // @formatter:on

    private final WebClient gitHubWebClient;

    @Override
    public Mono<List<RepositoryDto>> getUserRepositories(String userName) {
        return gitHubWebClient.get()
                .uri("/users/{userName}/repos", userName)
                .retrieve()
                .bodyToMono(REPOSITORY_LIST_TYPE)
                .flatMapIterable(Function.identity())
                .map(this::mapToDto)
                .flatMap(repo -> enrichWithBranchInfo(userName, repo))
                .collectList();
    }

    private Mono<RepositoryDto> enrichWithBranchInfo(String userName, RepositoryDto repository) {
        return gitHubWebClient.get()
                .uri("/repos/{userName}/{repository}/branches", userName, repository.getName())
                .retrieve()
                .bodyToMono(BRANCH_LIST_TYPE)
                .flatMapIterable(Function.identity())
                .map(this::mapToDto)
                .collectList()
                .map(branches -> {
                    repository.setBranches(branches);
                    return repository;
                });
    }

    private RepositoryDto mapToDto(Repository repository) {
        return RepositoryDto.builder()
                .name(repository.getName())
                .ownerLogin(repository.getOwner().getLogin())
                .build();
    }

    private BranchDto mapToDto(Branch branch) {
        return BranchDto.builder()
                .branchName(branch.getName())
                .commitSha(branch.getCommit().getSha())
                .build();
    }

}
