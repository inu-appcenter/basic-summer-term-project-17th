package dongmin.code.dongmin.global.exception.handler;

import dongmin.code.dongmin.global.exception.error.CustomErrorCode;
import dongmin.code.dongmin.global.exception.error.ErrorCode;
import dongmin.code.dongmin.global.exception.error.ErrorResponse;
import dongmin.code.dongmin.global.exception.error.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e) {
        log.error("RestApiException", e);
        return createResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = CustomErrorCode.INTERNAL_SERVER_ERROR;
        log.error("Exception", e);
        return createResponseEntity(errorCode);
    }

    // Exception에 맞는 ErrorCode 생성
    private ResponseEntity<ErrorResponse> createResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }
}