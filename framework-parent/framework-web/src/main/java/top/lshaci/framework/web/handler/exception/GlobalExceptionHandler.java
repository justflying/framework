package top.lshaci.framework.web.handler.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.common.exception.BaseException;
import top.lshaci.framework.web.enums.ErrorCode;
import top.lshaci.framework.web.model.JsonResponse;

/**
 * Global exception handler<br><br>
 * 
 * <b>0.0.4: </b>Add method argumentExceptionHandler
 * 
 * @author lshaci
 * @since 0.0.3
 * @version 0.0.4
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	
    /**
     * System exception log message
     */
    private static final String SYSTEM_EXCEPTION = "System be happend exception!";
	
    /**
     * Argument exception log message
     */
	private final static String FIELD = "字段:";

	/**
	 * Base exception handler
	 * 
	 * @param req the http servlet request
	 * @param e the exception
	 * @return json response
	 */
    @ExceptionHandler(BaseException.class)
    public JsonResponse<Object> baseExceptionHandler(HttpServletRequest req, Exception e) {
    	log.error(SYSTEM_EXCEPTION, e);
    	
        return JsonResponse
        		.failure(e.getMessage())
        		.setCode(ErrorCode.INTERNAL_PROGRAM_ERROR.getCode());
    }
    
	/**
	 * BindException and MethodArgumentNotValidException handler
	 * 
	 * @param req the http servlet request
	 * @param e the exception
	 * @return json response
	 */
    @ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class })
    public JsonResponse<Object> argumentExceptionHandler(HttpServletRequest req, Exception e) {
    	log.error(SYSTEM_EXCEPTION, e);
    	
        StringBuilder message = new StringBuilder();
        FieldError fieldError = null;
        
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            fieldError = bindException.getBindingResult().getFieldErrors().get(0);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) e;
            fieldError = argumentNotValidException.getBindingResult().getFieldErrors().get(0);
        }
        
        if (fieldError != null) {
        	String field = fieldError.getField();
        	log.warn(FIELD + field);
        	String msg = fieldError.getDefaultMessage();
        	message.append(msg);
		}

        return JsonResponse.failure(message.toString());
    }

	/**
	 * Default exception handler
	 * 
	 * @param req the http servlet request
	 * @param e the exception
	 * @return json response
	 */
    @ExceptionHandler(Exception.class)
    public JsonResponse<Object> defaultExceptionHandler(HttpServletRequest req, Exception e) {
    	log.error(SYSTEM_EXCEPTION, e);
    	
    	ErrorCode errorCode = ErrorCode.getByException(e);
    	
    	return JsonResponse
    			.failure(errorCode.getMsg())
    			.setCode(errorCode.getCode())
    			.addOtherData("detail", e.getMessage());
    }
    
}