package code.joonseo.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "User")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String part;

    @Column(nullable = false)
    private Double gen;

    @Column(name = "phone_num", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(Long id, String name, String part, Double gen, String phoneNumber, String email, String password) {
        this.id = id;
        this.name = name;
        this.part = part;
        this.gen = gen;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }


    public static User create(Long id, String name, String part, Double gen, String phoneNumber, String email, String password) {
        return User.builder()
                .email(email)
                .gen(gen)
                .id(id)
                .name(name)
                .part(part)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }

}
