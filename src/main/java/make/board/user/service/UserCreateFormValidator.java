package make.board.user.service;

import make.board.user.domain.SiteUser;
import make.board.user.repository.UserRepository;

public class UserCreateFormValidator {

    private final UserRepository userRepository;

    public UserCreateFormValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(SiteUser user) {
        if (user.getUsername() == null || user.getUsername().length() < 2 || user.getUsername().length() > 25) {
            throw new IllegalArgumentException("사용자ID는 2자 이상, 25자 이하여야 합니다.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수항목입니다.");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수항목입니다.");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}