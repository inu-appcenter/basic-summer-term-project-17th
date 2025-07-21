package code.joonseo.domain.user.controller;

import code.joonseo.domain.user.dto.res.UserInfoService;
import code.joonseo.domain.user.entity.User;
import code.joonseo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserInfoService userInfo) {
        User createdUser = userService.create(userInfo);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public  ResponseEntity<String> login(@RequestBody UserInfoService userInfo) {
        User loginUser = userService.login(userInfo.getId(), userInfo.getPassword());
        return ResponseEntity.ok("로그인 성공");
    }
}
