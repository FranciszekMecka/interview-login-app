package lloyd.hapag.mecka.franciszek.loginapp.user.controller;

import lloyd.hapag.mecka.franciszek.loginapp.user.dto.CreateUserDto;
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


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        User user = userService.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(userService.mapToDto(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createuserDto) {
        UserDto responseDto = userService.create(createuserDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<UserDto> patchUser(@PathVariable long id, @RequestBody UpdateUserDto request) {
        userService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserDto updatedUserDto = userService.update(request, id);

        return ResponseEntity.ok(updatedUserDto);

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.getById(id)
                .map(user -> {
                    userService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok("User deleted");
    }
}
