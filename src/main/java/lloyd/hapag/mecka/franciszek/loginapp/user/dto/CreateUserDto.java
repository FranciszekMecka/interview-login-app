package lloyd.hapag.mecka.franciszek.loginapp.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    private String username;
    private String gender;
    private int age;
}