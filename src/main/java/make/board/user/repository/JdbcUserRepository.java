package make.board.user.repository;

import javax.sql.DataSource;
import make.board.user.domain.SiteUser;

public class JdbcUserRepository implements UserRepository {

    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SiteUser create(SiteUser siteUser) {
        return null;
    }
}