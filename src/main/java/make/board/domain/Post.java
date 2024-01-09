package make.board.domain;

public class Post {
    //TODO: 번호가 자동부여되니 중복되는 번호가 생겼음, 해결방안: 번호는 앞선 ID의 +1로 책정할 것
    private Long id;
    private String inputName;
    private String inputTitle;
    private String inputContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputTitle() {
        return inputTitle;
    }

    public void setInputTitle(String inputTitle) {
        this.inputTitle = inputTitle;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }
}
