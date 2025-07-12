package dongmin.code.dongmin.domain.controller;

import dongmin.code.dongmin.domain.service.TaskService;
import dongmin.code.dongmin.domain.service.UserService;
import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 과제목록(/task)
// 과제조회(/task/search?taskID=?})
// 과제제출(/task/submit)
// 과제삭제(/task/delete?taskId=?})
// 과제수정(/task/update?taskId=?)

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> getAllTask(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<TaskDTO> getUser(@RequestParam Long taskId) {
        TaskDTO taskDTO = taskService.findById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskDTO);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitTask(@RequestBody TaskDTO taskDTO){
        taskService.submit(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Task{taskName=" + taskDTO.getTaskName() + "} posted successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Task{id=" + taskId + "} deleted  successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Task{id=" + taskDTO.getTaskId() + "} updated successfully");
    }
}
