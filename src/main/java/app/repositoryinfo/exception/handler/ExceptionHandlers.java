package app.repositoryinfo.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundClientException(NotFound ex) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAllException(Exception ex) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
