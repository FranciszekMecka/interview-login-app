package lloyd.hapag.mecka.franciszek.loginapp.user.controller;

import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UpdateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userService.convertToEntity(userDto);
        userService.create(user);
        UserDto responseDto = userService.convertToDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        User user = userService.find(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserDto userDto = userService.convertToDto(user);

        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable long id, @RequestBody UpdateUserDto request) {
        return userService.find(id)
                .map(user -> {
                    user.setGender(request.getGender());
                    user.setAge(request.getAge());
                    User updatedUser = userService.update(user);

                    return ResponseEntity.ok(userService.convertToDto(updatedUser));

                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.find(id)
                .map(user -> {
                    userService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
