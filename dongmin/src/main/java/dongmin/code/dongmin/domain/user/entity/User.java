package dongmin.code.dongmin.domain.user.entity;


import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "user_name")
    private String userName;

    @Column(name = "part")
    private String part;

    @Column(name = "gen")
    private Double gen;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "join_date")
    private String joinDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> taskList =  new ArrayList<>();

    // DTO -> Entity, Entity 반환
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.userName = userDTO.getUserName();
        user.part = userDTO.getPart();
        user.gen = userDTO.getGen();
        user.phoneNumber = userDTO.getPhoneNumber();
        user.joinDate = userDTO.getJoinDate();
        return user;
    }

    // DTO에 담긴 정보로 업데이트
    public void updateUser(UserDTO userDTO) {
        this.userName = userDTO.getUserName();
        this.part = userDTO.getPart();
        this.gen = userDTO.getGen();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.joinDate = userDTO.getJoinDate();
    }
}
