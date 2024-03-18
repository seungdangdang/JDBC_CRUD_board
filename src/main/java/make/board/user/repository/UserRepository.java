package make.board.user.repository;

import java.util.Optional;
import make.board.user.domain.SiteUser;

public interface UserRepository {

    SiteUser save(SiteUser siteUser);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
    Optional<SiteUser> findByUsername(String username);
}