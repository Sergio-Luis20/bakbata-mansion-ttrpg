package br.sergio.bakbata_mansion.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@StandardException
public class OrphanSheetException extends RuntimeException {
}
