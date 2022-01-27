package app.repositoryinfo.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Configuration
public class CustomErrorMessageConfig {
    public static final String KEY_STATUS = "status";
    public static final String KEY_ERROR = "error";
    public static final String KEY_MESSAGE = "message";

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> defaultMap = super.getErrorAttributes(request, options);

                return Map.of(KEY_STATUS, defaultMap.get(KEY_STATUS),
                        KEY_MESSAGE, defaultMap.get(KEY_ERROR));
            }
        };
    }

}