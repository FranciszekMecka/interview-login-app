package lloyd.hapag.mecka.franciszek.loginapp.user.service;

import lloyd.hapag.mecka.franciszek.loginapp.user.dto.CreateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UpdateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setAccountCreationTimestamp(user.getAccountCreationTimestamp());

        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setAccountCreationTimestamp(userDto.getAccountCreationTimestamp());

        return user;
    }

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    public UserDto create(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setGender(createUserDto.getGender());
        user.setAge(createUserDto.getAge());

        User newUser = repository.save(user);
        UserDto userResponse = new UserDto();
        userResponse.setId(newUser.getId());
        userResponse.setUsername(newUser.getUsername());
        userResponse.setGender(newUser.getGender());
        userResponse.setAge(newUser.getAge());
        userResponse.setAccountCreationTimestamp(newUser.getAccountCreationTimestamp());

        return userResponse;
    }

    public UserDto update(UpdateUserDto updateUserDto, long id) {
        User user = repository.findById(id).orElseThrow(NoSuchElementException::new);


        user.setAge(updateUserDto.getAge());
        user.setGender(updateUserDto.getGender());


        User updatedUser = repository.save(user);

        return mapToDto(updatedUser);
    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

}
