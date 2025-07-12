package dongmin.code.dongmin.domain.service;

import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.task.repository.TaskRepository;
import dongmin.code.dongmin.domain.user.dto.UserDTO;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TaskDTO findById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        return TaskDTO.toTaskDTO(task);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            taskDTOList.add(TaskDTO.toTaskDTO(task));
        }
        return taskDTOList;
    }

    @Transactional
    public void submit(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = Task.toTask(taskDTO, user);
        taskRepository.save(task);
    }

    @Transactional
    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void update(TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        task.updateTask(taskDTO, user);
    }
}
