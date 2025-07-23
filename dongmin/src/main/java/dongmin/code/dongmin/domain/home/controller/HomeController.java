package dongmin.code.dongmin.domain.home.controller;

import dongmin.code.dongmin.domain.home.service.HomeService;
import dongmin.code.dongmin.domain.user.dto.UserCreateRequestDTO;
import dongmin.code.dongmin.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.status(HttpStatus.OK).body("HomeController");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        homeService.signup(userCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
