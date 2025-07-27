package dongmin.code.dongmin.domain.task.service;

import dongmin.code.dongmin.domain.task.dto.TaskDTO;
import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.task.repository.TaskRepository;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import dongmin.code.dongmin.global.exception.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dongmin.code.dongmin.global.exception.error.CustomErrorCode.TASK_NOT_FOUND;
import static dongmin.code.dongmin.global.exception.error.CustomErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TaskDTO findById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

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
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        Task task = Task.create(taskDTO, user);
        taskRepository.save(task);
        return TaskDTO.create(task);
    }

    @Transactional
    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskDTO update(Long taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        task.update(taskDTO, user);
        return TaskDTO.create(task);
    }
}
