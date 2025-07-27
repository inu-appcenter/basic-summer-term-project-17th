package dongmin.code.dongmin.domain.task.controller;

import dongmin.code.dongmin.domain.task.service.TaskService;
import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTask(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> submitTask(@RequestBody TaskDTO taskDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.submit(taskDTO));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(taskId, taskDTO));
    }
}
