package code.joonseo.domain.user.controller;

import code.joonseo.domain.user.dto.res.UserInfoDto;
import code.joonseo.domain.user.dto.res.UserLoginDto;
import code.joonseo.domain.user.entity.User;
import code.joonseo.domain.user.service.UserService;
import code.joonseo.global.jwt.TokenResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    // 기본 유효성 검사
    public ResponseEntity<String> signup(@Valid @RequestBody UserInfoDto userInfo) {
        User createdUser = userService.create(userInfo);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public  ResponseEntity<TokenResponseDto> login(@RequestBody UserLoginDto userInfo) {
        TokenResponseDto responseDto = userService.login(userInfo.getEmail(), userInfo.getPassword());
        return ResponseEntity.ok().body(responseDto);
    }
}
