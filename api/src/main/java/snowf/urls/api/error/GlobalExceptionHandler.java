package snowf.urls.api.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import snowf.urls.api.dto.WebResponse;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String REQ_ID_HEADER = "X-Request-ID";
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> validationEx(ConstraintViolationException ex, HttpServletRequest http) {
        String requestId = http.getHeader(REQ_ID_HEADER);
        List<Map<String, String>> violationList = ex.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    Map<String, String> errorList = new HashMap<>();
                    errorList.put("Field", constraintViolation.getPropertyPath().toString());
                    errorList.put("Message", constraintViolation.getMessage());
                    return errorList;
                }).toList();

        /*
         * Put validation list to logger using mdc (ThreadContext Log4J)
         * */
        try {
            ThreadContext.put("violationList", objectMapper.writeValueAsString(violationList));
            log.error("Failed to make request. Reason : {}", ex.getMessage());
        } finally {
            ThreadContext.remove("violationList");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder()
                        .header(WebResponse.MessageHeader.builder()
                                .requestId(requestId)
                                .timestamp(OffsetDateTime.now().toString())
                                .message("Validation error")
                                .path(http.getRequestURI())
                                .build())
                        .errors(WebResponse.Errors.builder()
                                .errorCode("VALIDATION_ERROR")
                                .errorMessage("Validation error")
                                .details(violationList)
                                .build())
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> handleResponseException(ResponseStatusException ex, HttpServletRequest http) {
        String requestId = http.getHeader(REQ_ID_HEADER);
        log.error("{}", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode())
                .body(WebResponse.<String>builder()
                        .header(WebResponse.MessageHeader.builder()
                                .requestId(requestId)
                                .timestamp(OffsetDateTime.now().toString())
                                .path(http.getRequestURI())
                                .build())
                        .errors(WebResponse.Errors.builder()
                                .errorCode(ex.getStatusCode().toString())
                                .errorMessage(ex.getReason())
                                .build())
                        .build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleGenericException(Exception ex, HttpServletRequest http) {
        String requestId = http.getHeader(REQ_ID_HEADER);
        log.error("Failed to make request. Reason : Internal server error, details : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .header(WebResponse.MessageHeader.builder()
                                .requestId(requestId)
                                .timestamp(OffsetDateTime.now().toString())
                                .path(http.getRequestURI())
                                .build())
                        .errors(WebResponse.Errors.builder()
                                .errorCode("500 Internal Server Error")
                                .errorMessage("Internal Server Error")
                                .build())
                        .build());
    }
}
