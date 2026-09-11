package snowf.urls.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Table(name = "url")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "alias", length = 100)
    private String alias;

    @Column(name = "created_at",
            columnDefinition = "TIMESTAMPTZ DEFAULT NOW()",
            insertable = false,
            updatable = false)
    private OffsetDateTime createdAt;
}
