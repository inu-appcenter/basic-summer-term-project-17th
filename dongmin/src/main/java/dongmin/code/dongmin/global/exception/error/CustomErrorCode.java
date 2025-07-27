package dongmin.code.dongmin.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {

    // 404 NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404,"존재하지 않는 회원"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, 404,"존재하지 않는 과제"),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류 발생");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

}
