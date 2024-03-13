package make.board.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.sql.DataSource;
import make.board.domain.Post;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class JdbcPostRepository implements PostRepository {

    private final DataSource dataSource;

    public JdbcPostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static boolean generated = false;

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
    public Long getToTalPages() {
        //TODO: pageSize 다른 곳에서 상수로 들고 있도록
        int pageSize = 10;
        long totalPosts = countPosts();
        if (totalPosts == 0) {
            return 0L;
        } else {
            return (totalPosts - 1) / pageSize + 1;
        }
    }

    @Override
    public Long countPosts() {
        long totalPosts = 0L;
        String sql = "select COUNT(*) FROM post";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalPosts = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, preparedStatement, resultSet);
        }
        return totalPosts;
    }

    @Override
    public List<Post> findPostsByPage(Long pageNumber) {
        //TODO: pageSize 다른 곳에서 상수로 들고 있도록
        int pageSize = 10;
        String sql = "SELECT * FROM post ORDER BY created_at DESC LIMIT ? OFFSET ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, pageSize);
            pstmt.setLong(2, (pageNumber - 1) * pageSize);
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

            // executeCreate 메서드를 사용하여 DELETE 쿼리 실행
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

    @Override
    public Optional<Post> modify(Long id, String newContent) {
        String sql = "UPDATE post SET inputContent = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContent);
            pstmt.setLong(2, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("글이 수정되었습니다. ID: " + id);
                return Optional.of(new Post()); // 수정된 레코드가 있다면 해당 레코드를 반환하도록 수정
            } else {
                System.out.println("수정할 글이 없습니다. ID: " + id);
                return Optional.empty();
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

    @Override
    public void testDataGenerator() {
        String sql = "INSERT INTO post (name, title, content, created_at) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        if (!generated) {
            try {
                conn = getConnection();

                int numPosts = 1000;

                // 가짜 데이터 생성 및 삽입
                Random random = new Random();
                for (int i = 0; i < numPosts; i++) {
                    String inputName = "User" + (i + 1);
                    String inputTitle = "Title" + (i + 1);
                    String inputContent = "Content" + (i + 1);
                    // 현재 시간으로 설정
                    long currentTime = System.currentTimeMillis();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(currentTime);

                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, inputName);
                        pstmt.setString(2, inputTitle);
                        pstmt.setString(3, inputContent);
                        pstmt.setTimestamp(4, timestamp);
                        pstmt.executeUpdate();
                    }
                }

                System.out.println("가짜 데이터 생성 완료.");
                generated = true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}