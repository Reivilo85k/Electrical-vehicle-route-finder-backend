package finalproject.evroutefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @Email
    @Column(unique = true)
    @NotEmpty(message = "Username is required")
    private String email;

    @Column(name = "isAdmin")
    private boolean isAdmin;

    private Instant created;
    private boolean enabled;
}
