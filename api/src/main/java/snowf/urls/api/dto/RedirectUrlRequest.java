package snowf.urls.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RedirectUrlRequest {
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]+$",
            message = "Alias can only contain alphabetic characters, numbers, '-' and '_'"
    )
    private String url;
}
