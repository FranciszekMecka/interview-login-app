package lloyd.hapag.mecka.franciszek.loginapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lloyd.hapag.mecka.franciszek.loginapp.user.controller.UserController;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.CreateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UpdateUserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.dto.UserDto;
import lloyd.hapag.mecka.franciszek.loginapp.user.entity.User;
import lloyd.hapag.mecka.franciszek.loginapp.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserControllerTests.class)
@Import(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserDto userDto;
    private CreateUserDto createUserDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    public void init() {
        user = User.builder()
                .id(1)
                .username("testUser")
                .gender("Male")
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .age(25)
                .build();


        createUserDto = CreateUserDto.builder()
                .username("testUser")
                .gender("Male")
                .age(25)
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .username("testUser")
                .gender("Male")
                .accountCreationTimestamp(Timestamp.from(Instant.now()))
                .age(25)
                .build();

        updateUserDto = UpdateUserDto.builder()
                .gender("Female")
                .age(30)
                .build();
    }

    @Test
    public void UserController_CreateUser_ReturnCreated() throws Exception {
        when(userService.create(any(CreateUserDto.class))).thenReturn(userDto);

        ResultActions response = mockMvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto)));
        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    public void UserController_whenGetUser_thenReturnUserDto() throws Exception {
        when(userService.getById(1L)).thenReturn(Optional.of(user));
        when(userService.mapToDto(user)).thenReturn(userDto);

        String expectedJson = String.format(
                "{\"username\":\"%s\",\"gender\":\"%s\",\"age\":%d}",
                userDto.getUsername(), userDto.getGender(), userDto.getAge()
        );

        ResultActions response = mockMvc.perform(get("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));
        response
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void UserController_whenPatchUser_thenReturnUserDto() throws Exception {
        UserDto updatedUserDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .gender(user.getGender())
                .age(user.getAge())
                .accountCreationTimestamp(user.getAccountCreationTimestamp())
                .build();

        when(userService.getById(anyLong())).thenReturn(Optional.of(user));
        when(userService.update(any(UpdateUserDto.class), anyLong())).thenReturn(updatedUserDto);

        mockMvc.perform(patch("/api/users/{id}/update", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedUserDto.getId()))
                .andExpect(jsonPath("$.username").value(updatedUserDto.getUsername()))
                .andExpect(jsonPath("$.gender").value(updatedUserDto.getGender()))
                .andExpect(jsonPath("$.age").value(updatedUserDto.getAge()));
    }

    @Test
    public void UserController_whenDeleteUser_thenReturnVoid() throws Exception {

        doNothing().when(userService).delete(anyLong());
        when(userService.getById(anyLong())).thenReturn(Optional.of(user));

        ResultActions response = mockMvc.perform(delete("/api/users/{id}/delete", 1)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


}
