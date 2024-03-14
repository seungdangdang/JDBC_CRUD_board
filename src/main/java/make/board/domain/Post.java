package make.board.domain;

public class Post {
    //TODO: 번호가 자동부여되니 중복되는 번호가 생겼음, 해결방안: 번호는 앞선 ID의 +1로 책정할 것
    private Long postId;
    private String author;
    private String title;
    private String content;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
