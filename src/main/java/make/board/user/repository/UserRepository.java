package make.board.user.repository;

import make.board.user.domain.SiteUser;

public interface UserRepository {

    SiteUser create(SiteUser siteUser);

    boolean existsByUsername(String name);
}