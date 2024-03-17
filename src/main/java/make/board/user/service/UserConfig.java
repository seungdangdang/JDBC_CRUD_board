package make.board.user.service;


import javax.sql.DataSource;
import make.board.user.repository.JdbcUserRepository;
import make.board.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    private final DataSource dataSource;

    public UserConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository(), userCreateFormValidator());
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

    @Bean
    public UserCreateFormValidator userCreateFormValidator() {
        return new UserCreateFormValidator();
    }
}