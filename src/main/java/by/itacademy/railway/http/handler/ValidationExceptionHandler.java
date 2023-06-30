package by.itacademy.railway.http.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler() //тот exception который отлавливаем
    public String handlerException(Exception exception) {
//        return "error/error500";
        return "";
    }

}
