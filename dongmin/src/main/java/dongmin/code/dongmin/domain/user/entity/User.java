package dongmin.code.dongmin.domain.user.entity;

import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.user.dto.UserCreateRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "part")
    private String part;

    @Column(name = "gen")
    private Double gen;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> taskList =  new ArrayList<>();

    @Builder
    private User(Long id, String name, String email, String password,
                 String part, Double gen, String phoneNumber, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.part = part;
        this.gen = gen;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

    public static User create(UserCreateRequestDTO userCreateRequestDTO, String encodedPassword) {
        return User.builder()
                .name(userCreateRequestDTO.getName())
                .email(userCreateRequestDTO.getEmail())
                .password(encodedPassword)
                .part(userCreateRequestDTO.getPart())
                .gen(userCreateRequestDTO.getGen())
                .phoneNumber(userCreateRequestDTO.getPhoneNumber())
                .joinDate(userCreateRequestDTO.getJoinDate())
                .build();
    }

    public void update(UserCreateRequestDTO userCreateRequestDTO, String encodedPassword) {
        this.name = userCreateRequestDTO.getName();
        this.email = userCreateRequestDTO.getEmail();
        this.password = encodedPassword;
        this.part = userCreateRequestDTO.getPart();
        this.gen = userCreateRequestDTO.getGen();
        this.phoneNumber = userCreateRequestDTO.getPhoneNumber();
        this.joinDate = userCreateRequestDTO.getJoinDate();
    }
}
