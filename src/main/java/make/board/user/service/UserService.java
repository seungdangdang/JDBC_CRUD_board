package make.board.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import make.board.user.domain.SiteUser;
import make.board.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserCreateFormValidator userCreateFormValidator) {
        this.userRepository = userRepository;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    public void create(SiteUser siteUser) {
        userCreateFormValidator.validate(siteUser);
        siteUser.setPassword(encryptPassword(siteUser.getPassword()));
        userRepository.create(siteUser);
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