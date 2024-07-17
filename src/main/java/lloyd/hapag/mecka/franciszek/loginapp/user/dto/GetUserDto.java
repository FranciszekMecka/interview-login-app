package lloyd.hapag.mecka.franciszek.loginapp.user.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {
    private long id;
    private String username;
    private String gender;
    private int age;
    private Timestamp accountCreationTimestamp;
}
