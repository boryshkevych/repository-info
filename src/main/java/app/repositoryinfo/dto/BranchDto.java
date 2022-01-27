package app.repositoryinfo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchDto {
    private String branchName;
    private String commitSha;
}
