package sai_adapa.projs.inv_management.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidRequestException extends Exception{
    public InvalidRequestException(String message) {
        super(message);
    }
}
