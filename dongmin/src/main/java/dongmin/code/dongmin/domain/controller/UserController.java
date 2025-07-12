package dongmin.code.dongmin.domain.controller;

import dongmin.code.dongmin.domain.service.UserService;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 회원목록(/user)
// 회원조회(/user/search?id=?})
// 회원삭제(/user/delete?id=?})
// 회원수정(/user/update?id=?})

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTO> getUser(@RequestParam Long id) {
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User{name=" + userDTO.getUserName() + "} added successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User{id=" + id + "} deleted  successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User{id=" + userDTO.getId() + "} updated successfully");
    }
}
