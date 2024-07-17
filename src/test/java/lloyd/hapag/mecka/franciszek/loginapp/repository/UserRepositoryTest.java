package lloyd.hapag.mecka.franciszek.loginapp.repository;

import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveUser_ReturnUser() {
        User user = User.builder()
                .username("Franciszek Mecka")
                .gender("Male")
                .age(22)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindById_ReturnUser() {
        User user = User.builder()
                .username("John Doe")
                .gender("Male")
                .age(35)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());

        Assertions.assertThat(foundUserOptional).isPresent();
        User foundUser = foundUserOptional.get();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void UserRepository_DeleteUser_ReturnEmptyOptional() {
        User user = User.builder()
                .username("John Smith")
                .gender("Male")
                .age(44)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());

        Assertions.assertThat(foundUserOptional).isPresent();
        User foundUser = foundUserOptional.get();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());

        userRepository.delete(user);

        Assertions.assertThat(userRepository.findById(savedUser.getId())).isEmpty();

    }

}
