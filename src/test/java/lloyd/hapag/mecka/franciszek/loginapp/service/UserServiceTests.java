package lloyd.hapag.mecka.franciszek.loginapp.service;


import lloyd.hapag.mecka.franciszek.loginapp.user.config.AppConfig;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.repository.UserRepository;
import lloyd.hapag.mecka.franciszek.loginapp.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppConfig.class)
@DirtiesContext

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser_ReturnsGetUserDto() {
        User user = User.builder()
                .id(1)
                .username("John Doe")
                .gender("Male")
                .age(55)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        UserService userService = new UserService(userRepository, new ModelMapper());

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        Assertions.assertThat(createdUser).isNotNull();
    }

    @Test
    public void UserService_Find_ReturnsUser() {
        User user = User.builder()
                .id(1)
                .username("Andrew Smith")
                .gender("Male")
                .age(77)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        Optional<User> createdUser = userService.find(1L);
        Assertions.assertThat(createdUser).isNotNull();

    };

    @Test
    public void UserService_UpdateUser_ReturnsUser() {
        User user = User.builder()
                .id(1)
                .username("Andrew Smith")
                .gender("Male")
                .age(77)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        UserService userService = new UserService(userRepository, new ModelMapper());

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);


        assert user != null;
        User createdUser = userService.update(userService.convertToDto(user), user.getId());
        Assertions.assertThat(createdUser).isNotNull();

    }

    @Test
    public void UserService_Delete_ReturnsUser() {
        User user = User.builder()
                .id(1)
                .username("Andrew Smith")
                .gender("Male")
                .age(77)
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        assertAll(() -> userService.delete(1L));

    };

}
