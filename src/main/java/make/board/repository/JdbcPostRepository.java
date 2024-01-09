package make.board.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import make.board.domain.Post;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcPostRepository implements PostRepository {

    private final DataSource dataSource;

    public JdbcPostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Post save(Post post) {
        String sql = "INSERT INTO post(name, title, content) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, post.getInputName());
            pstmt.setString(2, post.getInputTitle());
            pstmt.setString(3, post.getInputContent());

            // SQL 문 실행하여 데이터베이스에 게시물 정보 삽입
            pstmt.executeUpdate();

            // 자동 생성된 키 값 조회
            rs = pstmt.getGeneratedKeys();

            // 조회된 결과가 있으면 해당 값을 게시물 객체에 설정
            if (rs.next()) {
                post.setId(rs.getLong(1));
            } else {
                // 조회된 결과가 없을 경우 예외 처리
                throw new SQLException("id 조회 실패");
            }

            // 게시물 객체 반환
            return post;
        } catch (Exception e) {
            // 예외 발생 시 IllegalStateException으로 래핑하여 던짐
            throw new IllegalStateException(e);
        } finally {
            // 데이터베이스 연결, SQL 문 실행 객체, 쿼리 결과 객체 등 자원 반환
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT id, name, title, content FROM post WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setInputName(rs.getString("name"));
                post.setInputTitle(rs.getString("title"));
                post.setInputContent(rs.getString("content"));
                return Optional.of(post);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Post> findAll() {
        String sql = "select * from post";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setInputName(rs.getString("name"));
                post.setInputTitle(rs.getString("title"));
                post.setInputContent(rs.getString("content"));
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from post where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            // executeUpdate 메서드를 사용하여 DELETE 쿼리 실행
            int rowsAffected = pstmt.executeUpdate();

            // 삭제된 행이 있는지 확인 (rowsAffected가 1 이상이어야 함)
            if (rowsAffected > 0) {
                System.out.println("글이 삭제되었습니다. ID: " + id);
            } else {
                System.out.println("삭제할 글이 없습니다. ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt, null);
        }
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
