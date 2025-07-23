package dongmin.code.dongmin.domain.task.service;

import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.task.repository.TaskRepository;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TaskDTO findById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        return TaskDTO.create(task);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            taskDTOList.add(TaskDTO.create(task));
        }
        return taskDTOList;
    }

    @Transactional
    public TaskDTO submit(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Task task = Task.create(taskDTO, user);
        taskRepository.save(task);
        return TaskDTO.create(task);
    }

    @Transactional
    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskDTO update(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        task.update(taskDTO, user);
        return TaskDTO.create(task);
    }
}
