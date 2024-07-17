package lloyd.hapag.mecka.franciszek.loginapp.user.service;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.GetUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public Optional<User> find(Long id) {
        return repository.findById(id);
    }

    public void create(User user) {
        repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    public GetUserDto convertToDto(User user) {
        return modelMapper.map(user, GetUserDto.class);
    }

    public User convertToEntity(GetUserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

}
