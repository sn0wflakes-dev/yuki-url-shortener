package snowf.urls.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenUrlRequest {
    @NotBlank(message = "Long URL field is required")
    private String longUrl;

    private String alias;
}
