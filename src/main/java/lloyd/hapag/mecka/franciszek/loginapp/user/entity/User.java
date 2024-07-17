package lloyd.hapag.mecka.franciszek.loginapp.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false, updatable = true)
    private String gender;

    @Column(nullable = false, updatable = true)
    private int age;

    @Column(nullable = false, updatable = false)
    private Timestamp accountCreationTimestamp;


    @PrePersist
    protected void onCreate() {
        accountCreationTimestamp = Timestamp.from(Instant.now());
    }
}
