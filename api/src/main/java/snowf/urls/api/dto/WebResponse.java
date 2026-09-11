package snowf.urls.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private MessageHeader header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Errors errors;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageHeader {
        private String requestId;
        private String timestamp;
        private String message;
        private String path;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Errors {
        private String errorCode;
        private String errorMessage;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object details;
    }
}
