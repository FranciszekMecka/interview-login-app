package lloyd.hapag.mecka.franciszek.loginapp.service;


import lloyd.hapag.mecka.franciszek.loginapp.user.config.AppConfig;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.CreateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UpdateUserDto;
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

        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("John Doe")
                .gender("Male")
                .age(55)
                .build();

        UserService userService = new UserService(userRepository);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto createdUser = userService.create(createUserDto);

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
        Optional<User> createdUser = userService.getById(1L);
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

        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .gender("Male")
                .age(77)
                .build();

        UserService userService = new UserService(userRepository);

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);


        assert user != null;
        UserDto createdUser = userService.update(updateUserDto, user.getId());
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
