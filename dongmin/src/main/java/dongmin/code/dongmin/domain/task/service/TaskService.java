package dongmin.code.dongmin.domain.task.service;

import dongmin.code.dongmin.domain.task.dto.TaskCreateRequestDTO;
import dongmin.code.dongmin.domain.task.dto.TaskResponseDTO;
import dongmin.code.dongmin.domain.task.entity.Task;
import dongmin.code.dongmin.domain.task.repository.TaskRepository;
import dongmin.code.dongmin.domain.user.entity.User;
import dongmin.code.dongmin.domain.user.entity.UserDetailsImpl;
import dongmin.code.dongmin.domain.user.repository.UserRepository;
import dongmin.code.dongmin.global.exception.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dongmin.code.dongmin.global.exception.error.CustomErrorCode.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

        return TaskResponseDTO.create(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAll() {
        List<Task> taskList = taskRepository.findAll();
        List<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();
        for (Task task : taskList) {
            taskResponseDTOList.add(TaskResponseDTO.create(task));
        }
        return taskResponseDTOList;
    }

    @Transactional
    public TaskResponseDTO submit(TaskCreateRequestDTO taskCreateRequestDTO, UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        Task task = Task.create(taskCreateRequestDTO, user);
        taskRepository.save(task);
        return TaskResponseDTO.create(task);
    }

    @Transactional
    public void delete(Long taskId, UserDetailsImpl userDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

        if(!task.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new RestApiException(DELETE_FORBIDDEN);
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public TaskResponseDTO update(Long taskId, TaskCreateRequestDTO taskCreateRequestDTO, UserDetailsImpl userDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RestApiException(TASK_NOT_FOUND));

        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(USER_NOT_FOUND));

        if(!task.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new RestApiException(CHANGE_FORBIDDEN);
        }

        task.update(taskCreateRequestDTO);
        return TaskResponseDTO.create(task);
    }
}
