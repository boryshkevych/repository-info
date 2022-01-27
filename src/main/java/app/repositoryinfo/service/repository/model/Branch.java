package app.repositoryinfo.service.repository.model;

import lombok.Data;

@Data
public class Branch {
    private String name;
    private Commit commit;
}
