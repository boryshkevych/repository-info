package app.repositoryinfo.exception.handler;

import lombok.Data;

@Data
public class ApiError {
    private final int status;
    private final String message;
}
