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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SiteUser create(String userName, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassWord(encryptPassword(password));
        this.userRepository.create(user);
        return user;
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