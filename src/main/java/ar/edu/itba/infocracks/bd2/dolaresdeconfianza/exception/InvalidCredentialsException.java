package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class InvalidCredentialsException extends Exception{
    private int status = 403;
}
