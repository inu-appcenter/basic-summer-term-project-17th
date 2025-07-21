package dongmin.code.dongmin.domain.user.entity;

import dongmin.code.dongmin.domain.task.entity.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

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

    public static User create(String name, String email, String encodedPassword,
                              String part, Double gen, String phoneNumber, LocalDate joinDate) {
        return User.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .part(part)
                .gen(gen)
                .phoneNumber(phoneNumber)
                .joinDate(joinDate)
                .build();
    }

    public void update(String name, String email, String encodedPassword, String part,
                       Double gen, String phoneNumber, LocalDate joinDate) {
        this.name = name;
        this.email = email;
        this.password = encodedPassword;
        this.part = part;
        this.gen = gen;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
