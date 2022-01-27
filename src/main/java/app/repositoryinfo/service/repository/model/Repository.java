package app.repositoryinfo.service.repository.model;

import lombok.Data;

@Data
public class Repository {
    private String name;
    private Owner owner;
}
