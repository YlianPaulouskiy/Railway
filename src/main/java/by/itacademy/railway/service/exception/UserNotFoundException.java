package by.itacademy.railway.service.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id = %d not found.".formatted(id) );
    }

}
