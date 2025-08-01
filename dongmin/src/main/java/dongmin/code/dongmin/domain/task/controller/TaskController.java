package dongmin.code.dongmin.domain.task.controller;

import dongmin.code.dongmin.domain.task.dto.TaskCreateRequestDTO;
import dongmin.code.dongmin.domain.task.service.TaskService;
import dongmin.code.dongmin.domain.task.dto.TaskResponseDTO;
import dongmin.code.dongmin.domain.user.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskResponseDTO>> getAllTask(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{taskId}/info")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> submitTask(@RequestBody TaskCreateRequestDTO taskCreateRequestDTO,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.submit(taskCreateRequestDTO, userDetails));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        taskService.delete(taskId, userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long taskId,
                                                      @RequestBody TaskCreateRequestDTO taskCreateRequestDTO,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(taskId, taskCreateRequestDTO, userDetails));
    }
}
