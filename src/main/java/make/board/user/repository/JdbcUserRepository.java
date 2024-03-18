package make.board.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import make.board.user.domain.SiteUser;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcUserRepository implements UserRepository {

    private final DataSource dataSource;

    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SiteUser save(SiteUser siteUser) {
        String sql = "INSERT INTO siteUser(username, password, email) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, siteUser.getUsername());
            pstmt.setString(2, siteUser.getPassword());
            pstmt.setString(3, siteUser.getEmail());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                siteUser.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }

            return siteUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM siteuser WHERE username = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            try {
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM siteuser WHERE email = ?";
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            try {
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}