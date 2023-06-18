package mobi.foo.bookCatalog.handler;

import mobi.foo.bookCatalog.Exception.BookNotFoundException;
import mobi.foo.bookCatalog.FooResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FooResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        String errorMessage = String.join(", ", errorMessages);

        FooResponse response = FooResponse.builder()
                .message(errorMessage)
                .statues(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<FooResponse> handleNoSuchElementException(BookNotFoundException  ex) {
        FooResponse fooResponse = FooResponse.builder().message(ex.getMessage()).statues(false).build();

        return new ResponseEntity<>(fooResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FooResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Failed to convert value of type '" + ex.getValue().getClass().getSimpleName() +
                "' to required type '" + ex.getRequiredType().getSimpleName() +
                "'; For input string: \"" + ex.getValue() + "\"";

        FooResponse response = FooResponse.builder()
                .message(errorMessage)
                .statues(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
