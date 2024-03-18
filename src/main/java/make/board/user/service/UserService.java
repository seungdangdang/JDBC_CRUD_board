package make.board.user.service;

import make.board.user.domain.SiteUser;
import make.board.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        siteUser.setPassword(passwordEncoder.encode(siteUser.getPassword()));
        userRepository.save(siteUser);
    }
}