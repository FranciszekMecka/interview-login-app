package lloyd.hapag.mecka.franciszek.loginapp.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {
    private String gender;
    private int age;
}
