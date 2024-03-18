package make.board.user.repository;

import make.board.user.domain.SiteUser;

public interface UserRepository {

    SiteUser save(SiteUser siteUser);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
}