package snowf.urls.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import snowf.urls.api.entity.UrlEntity;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<UrlEntity, String> {
    Optional<UrlEntity> findByLongUrl(String longUrl);
    Optional<UrlEntity> findByAlias(String alias);
}
