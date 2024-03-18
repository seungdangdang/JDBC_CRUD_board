package make.board.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import make.board.user.domain.SiteUser;
import make.board.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserCreateFormValidator userCreateFormValidator) {
        this.userRepository = userRepository;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @Transactional
    public void create(SiteUser siteUser) {
        if (userRepository.existsByUsername(siteUser.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }
        if (userRepository.existsByEmail(siteUser.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        siteUser.setPassword(encryptPassword(siteUser.getPassword()));
        userRepository.save(siteUser);
    }

    //TODO: encryptPassword 메서드 적절한 곳으로 이동시키기
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}